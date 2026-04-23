package com.example.demoSQL.service;

import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.AuthenticationRequest;
import com.example.demoSQL.dto.request.IntrospectRequest;
import com.example.demoSQL.dto.response.AuthenticationResponse;
import com.example.demoSQL.dto.response.IntrospectResponse;
import com.example.demoSQL.globalexceptionhandler.AppException;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import com.example.demoSQL.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    @NonFinal
    protected static final String SIGN_KEY =
            "1234567890123456789012345678901234567890123456789012345678901234";

    UserRepository userRepository;
    public IntrospectResponse introspect(IntrospectRequest introspectRequest)
    throws JOSEException , ParseException {
       var token = introspectRequest.getToken();
       JWSVerifier verifier = new MACVerifier(SIGN_KEY);
       SignedJWT signedJWT = SignedJWT.parse(token);
       Date ExpirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
       var verifiered = signedJWT.verify(verifier);
       return IntrospectResponse.builder()
               .valid(verifiered && ExpirationDate.after(new Date()))
               .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       boolean authenticated = passwordEncoder.matches(request.getPassword(),user.getPassword());
        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
       String token = generateToken(user.getUsername() );
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    public String generateToken(String username) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("DoanNgocNhan")
                .issueTime(new Date())
                .expirationTime(new Date(
                        new Date().getTime() + 3600 * 1000
                ))
                .claim("nhan","custom")
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

}