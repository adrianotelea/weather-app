package com.ao.weatherstats.webclient;

import com.ao.weatherstats.config.EnvConfig;
import com.ao.weatherstats.exception.exceptions.CityNotFoundException;
import com.ao.weatherstats.exception.exceptions.WeatherForecastException;
import com.ao.weatherstats.model.WeatherAveragesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Component
public class WeatherForecastWebClient {
    private final WebClient webClient;
    private final EnvConfig envConfig;

    public WeatherForecastWebClient(WebClient.Builder webClientBuilder, EnvConfig envConfig) {
        this.envConfig = envConfig;
        String baseUrl = String.format("http://%s:8090/api", this.envConfig.getExternalApiHost());
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<WeatherAveragesResponse> getWeatherForCity(String city) {
        return webClient.get()
                .uri("/{cityName}", city)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse ->
                    Mono.error(new CityNotFoundException(
                            "Error getting forecast for city: " + city,
                            clientResponse.statusCode().value()
                    ))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new WeatherForecastException(
                                "Forecast service unavailable.",
                                clientResponse.statusCode().value()
                        ))
                        )
                .bodyToMono(WeatherAveragesResponse.class)
                .onErrorResume(e -> {
                        if (e instanceof CityNotFoundException) {
                            return Mono.just(WeatherAveragesResponse.builder().build());
                        }
                        if (e instanceof WebClientException) {
                            return Mono.error(new WeatherForecastException("Forecast service unavailable.", 418));
                        }
                        return Mono.empty();
                });
    }
}
