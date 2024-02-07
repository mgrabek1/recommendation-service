package com.mgrabek.recommendationservice.exception;

import org.springframework.http.HttpStatus;

public class UnsupportedOperationException extends RecommendationServiceWebException {

    public UnsupportedOperationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
