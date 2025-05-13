package com.minibuddy.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SuccessCode{

    OK(HttpStatus.OK, "ok"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
