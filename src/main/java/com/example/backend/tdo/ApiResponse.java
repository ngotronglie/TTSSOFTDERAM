package com.example.backend.dto;

import java.time.LocalDateTime;

public class ApiResponse {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private Object data;
    private String errors;

    public ApiResponse() {
    }

    // Constructor
    public ApiResponse(String status, String message, LocalDateTime timestamp, Object data, String errors) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}