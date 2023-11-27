package com.ao.weatherstats.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class EnvConfig {
    private String cities;
    private String externalApiHost;

    /**
     * Get the default cities from an environment variable or a fallback default.
     * The cities are split at comma also taking into account whitespace.
     * @return List of cities.
     */
    public List<String> getCitiesList() {
        String[] splitCities = cities.split("\\s*,\\s*");
        if (splitCities.length > 0) {
            return Arrays.asList(splitCities);
        }
        return List.of("Cluj-Napoca");
    }
}
