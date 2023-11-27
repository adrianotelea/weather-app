package com.ao.weatherforecast.service;

import com.ao.weatherforecast.exception.exceptions.CityDataNotFoundException;
import com.ao.weatherforecast.exception.exceptions.ProcessingException;
import com.ao.weatherforecast.model.WeatherForecastResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Paths;

@Service
public class WeatherForecastService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<WeatherForecastResponse> getWeatherForCity(String cityName) {
        try {
            File file = Paths.get("src/main/resources/data/", cityName + ".json").toFile();
            if (!file.exists()) {
                return Mono.error(new CityDataNotFoundException("City data not found for: " + cityName));
            }

            WeatherForecastResponse data = objectMapper.readValue(file, WeatherForecastResponse.class);
            if (data.getForecast().isEmpty()) {
                return Mono.error(new CityDataNotFoundException("City data not found for: " + cityName));
            }

            return Mono.just(data);
        } catch (Exception e) {
            return Mono.error(new ProcessingException("Error processing city: " + cityName
                    + " error: " + e.getMessage()));
        }
    }
}
