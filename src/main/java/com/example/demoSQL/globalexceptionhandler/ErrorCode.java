package com.example.demoSQL.globalexceptionhandler;

public enum ErrorCode {
    USER_EXISTED(1001,"USER_EXISTED"),
    USER_NOT_EXISTED(1002,"USER_NOT_EXISTED"),
    USER_DISABLED(1003,"USER_DISABLED"),
    MISSING_REQUIRED(1004,"MISSING_REQUIRED"),
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
