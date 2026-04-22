package com.example.demoSQL.globalexceptionhandler;

public enum ErrorCode {
    KEY_INVALID(0001,"KEY_INVALID"),
    USER_EXISTED(1001,"USER_EXISTED"),
    USER_NOT_EXISTED(1002,"USER_NOT_EXISTED"),
    USER_DISABLED(1003,"USER_DISABLED"),
    MISSING_REQUIRED(1004,"MISSING_REQUIRED"),
    USERNAME_INVALID(1005,"USERNAME_INVALID"),
    PASSWORD_INVALID(1006,"PASSWORD_INVALID"),
    EMAIL_INVALID(1007,"EMAIL_INVALID"),
    NAME_EXISTED(1007,"NAME_EXISTED"),
    UNAUTHORIZED(1008,"UNAUTHORIZED"),
    UNCATEGORIZED_EXITED(9999,"UNCATEGORIZED EXCEPTION")

    ;
    private int code;
    private String message;
    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
