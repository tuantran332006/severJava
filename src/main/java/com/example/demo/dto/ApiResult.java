package com.example.demo.dto;

import java.time.LocalDateTime;

public class ApiResult<T> {

    /** API có thành công hay không */
    private boolean success;

    /** Thông điệp trả về cho client */
    private String message;

    /** Mã lỗi (frontend dùng để xử lý riêng) */
    private String errorCode;

    /** Dữ liệu trả về */
    private T data;

    /** Thời điểm phản hồi */
    private LocalDateTime timestamp;

    // =================== CONSTRUCTOR ===================

    public ApiResult() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResult(boolean success, String message, String errorCode, T data) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // =================== FACTORY METHODS ===================

    /**  Thành công – không data */
    public static <T> ApiResult<T> ok(String message) {
        return new ApiResult<>(true, message, null, null);
    }

    /** Thành công – có data */
    public static <T> ApiResult<T> ok(String message, T data) {
        return new ApiResult<>(true, message, null, data);
    }

    /**  Thất bại – message đơn giản */
    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(false, message, "ERROR", null);
    }

    /**  Thất bại – có errorCode */
    public static <T> ApiResult<T> fail(String errorCode, String message) {
        return new ApiResult<>(false, message, errorCode, null);
    }

    // =================== GETTER / SETTER ===================

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}