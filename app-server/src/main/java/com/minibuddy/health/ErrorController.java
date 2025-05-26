package com.minibuddy.health;

import com.minibuddy.global.error.exception.CustomException;
import com.minibuddy.global.error.code.ErrorCode;
import com.minibuddy.global.error.code.UserErrorCode;
import com.minibuddy.global.response.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/5xx")
    public SuccessResponse<?> error5xx() {
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/custom")
    public SuccessResponse<?> errorCustom() {
        throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }
}
