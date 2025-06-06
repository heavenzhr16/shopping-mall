package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

<<<<<<< HEAD
    // 공통 메서드
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String errorCode, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "errorCode", errorCode,
                "message", message
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        return buildErrorResponse("BAD_REQUEST", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        e.printStackTrace(); // 디버깅용 로그
        return buildErrorResponse("INTERNAL_ERROR", "알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
=======
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(Map.of(
                "status", errorCode.getStatus(),
                "errorCode", errorCode.getCode(),
                "message", errorCode.getMessage()
            ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(Map.of(
                "status", errorCode.getStatus(),
                "errorCode", errorCode.getCode(),
                "message", errorCode.getMessage()
            ));
>>>>>>> 4155211dd837ee599b5279362cd727339fa5ae23
    }
}