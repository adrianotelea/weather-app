package com.ao.weatherstats.service;

import com.ao.weatherstats.config.EnvConfig;
import com.ao.weatherstats.exception.exceptions.InvalidRequestException;
import com.ao.weatherstats.model.Forecast;
import com.ao.weatherstats.model.WeatherAveragesResponse;
import com.ao.weatherstats.model.WeatherData;
import com.ao.weatherstats.webclient.WeatherForecastWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherForecastWebClient weatherForecastWebClient;

    @Mock
    private EnvConfig envConfig;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void testGetAveragesForCities_WithValidCities() {
        List<String> cities = List.of("Bucuresti", "London");
        WeatherAveragesResponse bucurestiResponse = WeatherAveragesResponse.builder()
                .forecast(List.of(Forecast.builder()
                        .temperature(BigDecimal.valueOf(20))
                        .wind(BigDecimal.TEN)
                        .build()))
                .build();

        when(envConfig.getCitiesList()).thenReturn(Collections.singletonList("Bucuresti"));
        when(weatherForecastWebClient.getWeatherForCity("Bucuresti")).thenReturn(Mono.just(bucurestiResponse));

        StepVerifier.create(weatherService.getAveragesForCities(cities))
                .expectNextMatches(response -> {
                    WeatherData londonData = response.result().get(0);
                    return londonData.getName().equals("Bucuresti") &&
                            londonData.getTemperature().compareTo(BigDecimal.valueOf(20)) == 0 &&
                            londonData.getWind().compareTo(BigDecimal.valueOf(10)) == 0;
                })
                .verifyComplete();
    }

    @Test
    void testGetAveragesForCities_WithInvalidCity() {
        List<String> cities = List.of("12345");

        assertThrows(InvalidRequestException.class, () -> weatherService.getAveragesForCities(cities));
    }

    @Test
    void testGetAveragesForCities_WithNoCitiesProvided() {
        List<String> cities = Collections.emptyList();

        assertThrows(InvalidRequestException.class, () -> weatherService.getAveragesForCities(cities));
    }
}

