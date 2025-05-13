package com.minibuddy.global.error.code;

import com.minibuddy.global.error.exception.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeIfs {

    SAMPLE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Sample Error Message"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
