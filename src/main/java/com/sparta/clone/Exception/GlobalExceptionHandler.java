package com.sparta.clone.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected  ResponseEntity<ErrorResponse> handlingCustomException(GlobalException e){
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}