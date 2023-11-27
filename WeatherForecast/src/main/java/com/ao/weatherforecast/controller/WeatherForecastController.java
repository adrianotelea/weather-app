package com.ao.weatherforecast.controller;

import com.ao.weatherforecast.model.WeatherForecastResponse;
import com.ao.weatherforecast.service.WeatherForecastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class WeatherForecastController {
    private final WeatherForecastService weatherForecastService;

    public WeatherForecastController(final WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping("/{cityName}")
    public Mono<WeatherForecastResponse> getWeatherForecast(@PathVariable String cityName) {
        return weatherForecastService.getWeatherForCity(cityName);
    }
}
