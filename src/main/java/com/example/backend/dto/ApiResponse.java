package com.example.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
    private List<String> errors;  // Thay đổi từ String thành List<String>

    public ApiResponse() {
    }

    // Constructor
    public ApiResponse(String status, String message, LocalDateTime timestamp, T data, List<String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
        this.errors = errors;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
