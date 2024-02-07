package com.mgrabek.recommendationservice.model;

import org.springframework.http.HttpStatus;

public record ErrorMessageDto(String message, HttpStatus httpStatus) {
}
