package com.minibuddy.global.response;

public record SuccessResponse<T>(
        Result result,
        T data
) {
    public static <T> SuccessResponse<T> ok(T data) {
        return new SuccessResponse<>(
                Result.ok(),
                data
        );
    }
    public static <T> SuccessResponse<T> success(T data, SuccessCode successCode) {
        return new SuccessResponse<>(
                Result.success(successCode),
                data
        );
    }
}
