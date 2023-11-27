package com.ao.weatherforecast.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WeatherForecastResponse {
    private BigDecimal temperature;
    private BigDecimal wind;
    private String description;
    private List<Forecast> forecast;
}
