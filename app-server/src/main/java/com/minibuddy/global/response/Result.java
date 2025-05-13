package com.minibuddy.global.response;

import com.minibuddy.global.error.exception.ErrorCodeIfs;

import java.time.LocalDateTime;

/**
 * Record class that defines the standard format for API responses.
 * <p>
 * All success/failure responses are serialized into the JSON structure below:
 * <pre>{@code
 * {
 *   "result": {
 *     "resultCode": 200,
 *     "resultMessage": "Sample Success",
 *     "timestamp": "2025-04-11T16:22:46.586556"
 *   }
 * }}</pre>
 * </p>
 *
 * @see SuccessCode HTTP 2xx Success Code Enumeration
 * @see ErrorCodeIfs Custom error code interface
 */
public record Result (
        Integer resultCode,
        String resultMessage,
        LocalDateTime timestamp
) {
    public static Result ok() {
        return new Result(
                SuccessCode.OK.getHttpStatus().value(),
                SuccessCode.OK.getMessage(),
                LocalDateTime.now()
        );
    }
    public static Result success(SuccessCode successCode) {
        return new Result(
                successCode.getHttpStatus().value(),
                successCode.getMessage(),
                LocalDateTime.now()
        );
    }
    public static Result error(ErrorCodeIfs errorCodeIfs) {
        return new Result(
                errorCodeIfs.getHttpStatus().value(),
                errorCodeIfs.getMessage(),
                LocalDateTime.now()
        );
    }
}
