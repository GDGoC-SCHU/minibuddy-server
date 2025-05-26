package com.minibuddy.global.error.code;

import com.minibuddy.global.error.exception.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FirebaseErrorCode implements ErrorCodeIfs {

    INVALID_FIREBASE_UID(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid firebase uid"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}