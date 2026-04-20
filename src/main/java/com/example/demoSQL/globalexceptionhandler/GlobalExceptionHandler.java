package com.example.demoSQL.globalexceptionhandler;

import com.example.demoSQL.dto.ApiResponse;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .code(errorCode.getCode())
                .result(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handleAppException(RuntimeException ex){
        ApiResponse apiResponse =  ApiResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_EXITED.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXITED.getMessage())
                .result(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String enumsKey = ex.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.KEY_INVALID;
        try {
            errorCode = ErrorCode.valueOf(enumsKey);
        } catch (IllegalArgumentException e) {
        }
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .result(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
