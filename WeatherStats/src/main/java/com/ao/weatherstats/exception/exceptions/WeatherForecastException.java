package com.ao.weatherstats.exception.exceptions;

import lombok.Getter;

@Getter
public class WeatherForecastException extends RuntimeException {
    private final int statusCode;

    public WeatherForecastException(String message, int code) {
        super(message);
        this.statusCode = code;
    }
}
