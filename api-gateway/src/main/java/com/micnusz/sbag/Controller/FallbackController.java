package com.micnusz.sbag.Controller;

import com.micnusz.sbag.Model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping
    public ResponseEntity<ErrorResponse> fallback(ServerHttpRequest request) {

        String originalPath = request.getHeaders()
                .getFirst("X-Original-Request-Url");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse(
                        Instant.now(),
                        503,
                        "USER_SERVICE_UNAVAILABLE",
                        "User service is temporarily unavailable",
                        originalPath != null ? originalPath : request.getURI().getPath()
                ));
    }
}
