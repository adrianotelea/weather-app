package com.ao.weatherstats.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WeatherData {
    private String name;
    private BigDecimal temperature;
    private BigDecimal wind;
}
