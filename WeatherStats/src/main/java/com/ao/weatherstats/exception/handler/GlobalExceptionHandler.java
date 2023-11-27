package com.ao.weatherstats.exception.handler;

import com.ao.weatherstats.exception.exceptions.InvalidRequestException;
import com.ao.weatherstats.exception.exceptions.WeatherForecastException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    public Mono<ResponseEntity<String>> handleInvalidRequestException(InvalidRequestException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage()));
    }

    @ExceptionHandler(WeatherForecastException.class)
    public Mono<ResponseEntity<String>> handleWeatherForecastException(WeatherForecastException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage()));
    }
}
