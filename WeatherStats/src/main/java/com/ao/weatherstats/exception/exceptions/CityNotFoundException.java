package com.ao.weatherstats.exception.exceptions;

import lombok.Getter;

@Getter
public class CityNotFoundException extends RuntimeException {
    private final int statusCode;

    public CityNotFoundException(String message, int code) {
        super(message);
        this.statusCode = code;
    }
}