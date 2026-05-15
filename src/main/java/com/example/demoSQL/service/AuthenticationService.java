package com.example.demoSQL.service;

import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.AuthenticationRequest;
import com.example.demoSQL.dto.request.IntrospectRequest;
import com.example.demoSQL.dto.request.LogoutRequest;
import com.example.demoSQL.dto.request.RefreshRequest;
import com.example.demoSQL.dto.response.AuthenticationResponse;
import com.example.demoSQL.dto.response.IntrospectResponse;
import com.example.demoSQL.entity.InvalidatedToken;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.globalexceptionhandler.AppException;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import com.example.demoSQL.repository.InvalidatedTokenRepository;
import com.example.demoSQL.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;

import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    InvalidatedTokenRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY ;
    UserRepository userRepository;
    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
    throws JOSEException , ParseException {
        boolean valid = true;
        try{
            verifyToken(introspectRequest.getToken());
        }catch (AppException e){
            valid = false;
        }
    return IntrospectResponse.builder()
            .valid(valid)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
       boolean authenticated = passwordEncoder.matches(request.getPassword(),user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
       String token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("DoanNgocNhan")
                .issueTime(new Date())
                .expirationTime(new Date(
                        new Date().getTime() + 3600 * 1000
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(SIGN_KEY));
        }catch(JOSEException e){
            throw new AppException(ErrorCode.TOKEN_CANNOT_CREATE);
        }
       return jwsObject.serialize();
    }
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                    role.getPermissions().forEach(permission ->
                            stringJoiner.add(permission.getName())
                    );
                }
            });
        }

        return stringJoiner.toString();
    }
    public void Logout(LogoutRequest logoutRequest) throws JOSEException , ParseException {
        var signedToken = verifyToken(logoutRequest.getToken());
        Date ExpirationDate = signedToken.getJWTClaimsSet().getExpirationTime();
        String jwtid = signedToken.getJWTClaimsSet().getJWTID();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .expiryDate(ExpirationDate)
                .id(jwtid)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
    }
    public SignedJWT verifyToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date ExpirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        if(!(verified || ExpirationDate.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if(invalidatedTokenRepository.findById(signedJWT.getJWTClaimsSet().getJWTID()).isPresent()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return signedJWT;
    }
    public AuthenticationResponse refreshToken(RefreshRequest request)  throws JOSEException, ParseException {
      var signedJWT = verifyToken(request.getToken());
      String jwtid = signedJWT.getJWTClaimsSet().getJWTID();
      String userName = signedJWT.getJWTClaimsSet().getSubject();
      Date ExpirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
      InvalidatedToken invalidatedToken = InvalidatedToken.builder()
              .id(jwtid)
              .expiryDate(ExpirationDate)
              .build();
      invalidatedTokenRepository.save(invalidatedToken);
      User user = userRepository.findByUsername(userName).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
      var token = generateToken(user);
      return AuthenticationResponse.builder()
              .token(token)
              .authenticated(true)
              .build();

    }

}