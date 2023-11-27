package com.ao.weatherforecast.exception.exceptions;

public class CityDataNotFoundException extends RuntimeException {
    public CityDataNotFoundException(String message) {
        super(message);
    }
}