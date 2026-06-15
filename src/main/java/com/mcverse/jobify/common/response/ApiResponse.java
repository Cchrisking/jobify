package com.mcverse.jobify.common.response;

public record ApiResponse<T>(boolean success, String message, T data, int status) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "OK", data, 200);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, "Created", data, 201);
    }

    public static ApiResponse<Void> error(String message, int status) {
        return new ApiResponse<>(false, message, null, status);
    }
}
