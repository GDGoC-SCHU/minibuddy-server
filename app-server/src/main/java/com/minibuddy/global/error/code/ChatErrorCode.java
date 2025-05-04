package com.minibuddy.global.error.code;

import com.minibuddy.global.error.exception.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatErrorCode implements ErrorCodeIfs {

    NOT_FOUND(HttpStatus.NOT_FOUND, "Chat stat not found"),
    NO_SUCH_CHAT_STRATEGY(HttpStatus.INTERNAL_SERVER_ERROR, "No such chat strategy"),
    UNSUPPORTED_RESPONSE_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "No supported chat types found");

    private final HttpStatus httpStatus;
    private final String message;
}
