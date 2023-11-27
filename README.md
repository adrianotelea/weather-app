# About

## WeatherStats

A Reactive Spring Boot App that exposes an endpoint which receives a list of cities.
The app calls an external API that returns forecast mock data. Using the mock data average
wind and temperature are returned, and a CSV file is generated in its root.

The app only returns values for a predefined list of cities, configurable through environment variables.
If the variable DEFAULT_CITIES does not exist, it will take a predefined list of cities.

### Technologies
Java 17, Spring Boot, Gradle 8

## WeatherForecast
A Spring Boot App that returns a list of mock forecast data.

### Technologies
Java 17, Spring Boot, Gradle 8

## weather-forecast-ui
A simple React UI that can be used to test the app.

Requires Node 20. Can be started using 'npm run dev' in the root of the project.


## Docker configuration
A docker-compose configuration is available to run the two Spring Boot APIs.