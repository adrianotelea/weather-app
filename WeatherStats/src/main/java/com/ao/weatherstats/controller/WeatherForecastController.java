package com.ao.weatherstats.controller;

import com.ao.weatherstats.model.WeatherDataResponse;
import com.ao.weatherstats.service.WeatherService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherForecastController {
    private final WeatherService weatherService;

    public WeatherForecastController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    @CrossOrigin
    public Mono<WeatherDataResponse> getAverageTemperatureAndWind(@RequestParam List<String> city) {
        return weatherService.getAveragesForCities(city);
    }
}