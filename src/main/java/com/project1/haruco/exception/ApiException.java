package com.project1.haruco.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus badRequest) {
        this.message = message;
        this.httpStatus = badRequest;
    }
}
