package com.example.backend.exception;

import com.example.backend.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi không tìm thấy entity (ví dụ: không tìm thấy City với ID nào đó)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                ex.getMessage(),
                LocalDateTime.now(),
                null,
                List.of(ex.getMessage()) // Trả về lỗi dưới dạng danh sách
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Xử lý lỗi validate từ @Valid (ví dụ: tên không được rỗng)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        // Lấy danh sách các lỗi
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage) // Lấy thông báo lỗi từ mỗi trường
                .collect(Collectors.toList());

        // Trả về lỗi dưới dạng danh sách
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                "Dữ liệu không hợp lệ",
                LocalDateTime.now(),
                null,
                errorMessages // Trả về mảng lỗi
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Xử lý lỗi hệ thống hoặc exception chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                "Đã xảy ra lỗi hệ thống",
                LocalDateTime.now(),
                null,
                List.of(ex.getMessage()) // Trả về lỗi dưới dạng danh sách
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
