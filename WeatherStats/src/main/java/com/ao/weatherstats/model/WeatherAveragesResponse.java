package com.ao.weatherstats.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class WeatherAveragesResponse {
    private BigDecimal temperature;
    private BigDecimal wind;
    private String description;
    private List<Forecast> forecast;
}
