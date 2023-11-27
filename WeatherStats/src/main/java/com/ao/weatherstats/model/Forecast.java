package com.ao.weatherstats.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Forecast {
    private Integer day;
    private BigDecimal temperature;
    private BigDecimal wind;
}
