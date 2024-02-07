package com.mgrabek.recommendationservice.handler;

import com.mgrabek.recommendationservice.exception.RecommendationServiceWebException;
import com.mgrabek.recommendationservice.model.ErrorMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecommendationServiceExceptionHandler {

    @ExceptionHandler(RecommendationServiceWebException.class)
    public ResponseEntity<ErrorMessageDto> onErrorResponse(RecommendationServiceWebException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorMessageDto(exception.getMessage(), exception.getHttpStatus()));
    }

}
