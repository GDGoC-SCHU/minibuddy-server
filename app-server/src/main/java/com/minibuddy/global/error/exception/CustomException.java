package com.minibuddy.global.error.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;

    public CustomException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getMessage());
        this.errorCodeIfs = errorCodeIfs;
    }

    public CustomException(ErrorCodeIfs errorCodeIfs, Throwable cause) {
        super(cause);
        this.errorCodeIfs = errorCodeIfs;
    }
}
