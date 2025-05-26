package com.minibuddy.global.error;

import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.error.exception.ErrorCodeIfs;
import com.minibuddy.global.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(value = Integer.MIN_VALUE)
public class CustomExceptionHandler {

    /**
     * CustomException Handler
     */
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> customExceptionHandler(CustomException customException) {
        log.error("{}", customException.getErrorCodeIfs().getMessage());

        ErrorCodeIfs errorCodeIfs = customException.getErrorCodeIfs();
        Result result = Result.error(errorCodeIfs);
        return ResponseEntity
                .status(result.resultCode())
                .body(result);
    }

}
