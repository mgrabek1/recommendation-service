package com.mgrabek.recommendationservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecommendationServiceWebException extends RuntimeException {

    private final HttpStatus httpStatus;

    public RecommendationServiceWebException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
