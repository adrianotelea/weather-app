package com.ao.weatherstats.service;

import com.ao.weatherstats.config.EnvConfig;
import com.ao.weatherstats.exception.exceptions.InvalidRequestException;
import com.ao.weatherstats.model.Forecast;
import com.ao.weatherstats.model.WeatherAveragesResponse;
import com.ao.weatherstats.model.WeatherData;
import com.ao.weatherstats.model.WeatherDataResponse;
import com.ao.weatherstats.webclient.WeatherForecastWebClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class WeatherService {
    private final WeatherForecastWebClient weatherForecastClient;
    private final EnvConfig envConfig;

    public WeatherService(WeatherForecastWebClient weatherForecastClient, EnvConfig envConfig) {
        this.weatherForecastClient = weatherForecastClient;
        this.envConfig = envConfig;
    }

    public Mono<WeatherDataResponse> getAveragesForCities(final List<String> cities) {
        validateInput(cities);
        Flux<WeatherData> averagesByCity = Flux.fromIterable(cities)
                .distinct()
                .filter(envConfig.getCitiesList()::contains)
                .flatMap(this::getAverageWeather)
                .cache();

        Mono<List<WeatherData>> sortedWeatherDataMono =
                averagesByCity.collectSortedList(Comparator.comparing(WeatherData::getName));

        Mono<String> csvMono = sortedWeatherDataMono
                .map(this::convertToCsvString)
                .doOnSuccess(this::saveCsvToFile);

        return Mono.zip(sortedWeatherDataMono, csvMono,
                (sortedWeatherData, csv) -> new WeatherDataResponse(sortedWeatherData));
    }

    private String convertToCsvString(List<WeatherData> weatherDataList) {
        StringBuilder sb = new StringBuilder("Name,Temperature,Wind\n");
        weatherDataList
                .forEach(data -> sb.append(String.format("%s,%s,%s\n", data.getName(),
                        data.getTemperature(), data.getWind())));
        return sb.toString();
    }

    private void saveCsvToFile(String csvContent) {
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(Paths.get("weather_data.csv")))) {
            out.print(csvContent);
        } catch (Exception e) {
            // Handle the error
        }
    }

    public Mono<WeatherData> getAverageWeather(final String city) {
        return weatherForecastClient.getWeatherForCity(city)
                .map(res -> calculateAverages(res, city));
    }

    private WeatherData calculateAverages(final WeatherAveragesResponse weatherAverages, final String city) {
        boolean hasForecastData = Optional.ofNullable(weatherAverages)
                .map(WeatherAveragesResponse::getForecast)
                .filter(forecasts -> !forecasts.isEmpty())
                .isPresent();

        if (hasForecastData) {
            List<Forecast> forecastList = weatherAverages.getForecast();

            BigDecimal totalWind = forecastList.stream()
                    .map(Forecast::getWind)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalTemp = forecastList.stream()
                    .map(Forecast::getTemperature)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);;

            int size = forecastList.size();
            BigDecimal averageWind = totalWind.divide(BigDecimal.valueOf(size), 1, RoundingMode.HALF_UP);
            BigDecimal averageTemp = totalTemp.divide(BigDecimal.valueOf(size), 1, RoundingMode.HALF_UP);

            return WeatherData.builder()
                    .name(city)
                    .temperature(averageTemp)
                    .wind(averageWind)
                    .build();
        }

        return WeatherData.builder()
                .name(city)
                .temperature(null)
                .wind(null)
                .build();
    }

    private void validateInput(List<String> cities) {
        final Pattern VALID_CITIES = Pattern.compile("^[a-zA-Z-\\s]+$");
        if (cities.isEmpty() || !cities.stream().allMatch(VALID_CITIES.asPredicate())) {
            throw new InvalidRequestException("Invalid request");
        }
    }
}

