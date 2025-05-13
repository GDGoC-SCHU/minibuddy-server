package com.minibuddy.global.error.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeIfs {

    HttpStatus getHttpStatus();
    String getMessage();
}
