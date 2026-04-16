package com.example.demoSQL.globalexceptionhandler;

import com.example.demoSQL.dto.ApiResponse;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ErrorCode errorMessage =ex.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(errorMessage.getMessage());
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setResult(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handleAppException(RuntimeException ex){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXITED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXITED.getMessage());
        apiResponse.setResult(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        apiResponse.setMessage(message);
        apiResponse.setCode(ErrorCode.MISSING_REQUIRED.getCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
