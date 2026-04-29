package com.example.demoSQL.globalexceptionhandler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    KEY_INVALID(
            1000,
            "KEY_INVALID",
            HttpStatus.BAD_REQUEST
    ),

    USER_EXISTED(
            1001,
            "USER_EXISTED",
            HttpStatus.BAD_REQUEST
    ),

    USER_NOT_EXISTED(
            1002,
            "USER_NOT_EXISTED",
            HttpStatus.NOT_FOUND
    ),

    USER_DISABLED(
            1003,
            "USER_DISABLED",
            HttpStatus.FORBIDDEN
    ),

    MISSING_REQUIRED(
            1004,
            "MISSING_REQUIRED",
            HttpStatus.BAD_REQUEST
    ),

    USERNAME_INVALID(
            1005,
            "USERNAME_INVALID",
            HttpStatus.BAD_REQUEST
    ),

    PASSWORD_INVALID(
            1006,
            "PASSWORD_INVALID",
            HttpStatus.BAD_REQUEST
    ),

    EMAIL_INVALID(
            1007,
            "EMAIL_INVALID",
            HttpStatus.BAD_REQUEST
    ),

    NAME_EXISTED(
            1008,
            "NAME_EXISTED",
            HttpStatus.BAD_REQUEST
    ),

    UNAUTHENTICATED(
            1009,
            "UNAUTHENTICATED",
            HttpStatus.UNAUTHORIZED
    ),

    UNAUTHORIZED(
            1010,
            "UNAUTHORIZED",
            HttpStatus.FORBIDDEN
    ),

    TOKEN_CANNOT_CREATE(
            1011,
            "TOKEN_CANNOT_CREATE",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    TOKEN_INVALID(
            1012,
            "TOKEN_INVALID",
            HttpStatus.UNAUTHORIZED
    ),

    UNCATEGORIZED_EXCEPTION(
            9999,
            "UNCATEGORIZED_EXCEPTION",
            HttpStatus.INTERNAL_SERVER_ERROR
    );

    int code;
    String message;
    HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}