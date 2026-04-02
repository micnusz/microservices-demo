package com.micnusz.orderService.Handler;

import com.micnusz.orderService.Exception.UserNotFoundException;
import com.micnusz.orderService.Exception.UserServiceUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.status(404)
                .body(Map.of("error", "User not found"));
    }

    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<?> handleDown() {
        return ResponseEntity.status(503)
                .body(Map.of("error", "User service unavailable"));
    }
}

