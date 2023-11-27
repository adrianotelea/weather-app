package com.ao.weatherforecast.exception.handler;

import com.ao.weatherforecast.exception.exceptions.CityDataNotFoundException;
import com.ao.weatherforecast.exception.exceptions.ProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CityDataNotFoundException.class)
    public Mono<ResponseEntity<String>> handleCityNotFoundException(CityDataNotFoundException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()));
    }

    @ExceptionHandler(ProcessingException.class)
    public Mono<ResponseEntity<String>> handleProcessingException(ProcessingException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage()));
    }
}
