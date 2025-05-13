package com.minibuddy.global.error;

import com.minibuddy.global.error.code.ErrorCode;
import com.minibuddy.global.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 5xx Exception Handler
     **/
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Result> handlerException(Exception e) {
        log.error("Internal Server Error : {}", e.getMessage());
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        Result error5xx = Result.error(errorCode);
        return ResponseEntity.status(error5xx.resultCode()).body(error5xx);
    }
}
