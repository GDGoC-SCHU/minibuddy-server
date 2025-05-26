package com.minibuddy.global.error.code;

import com.minibuddy.global.error.exception.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    FAILED_TO_DELETE_USER(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete user"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
