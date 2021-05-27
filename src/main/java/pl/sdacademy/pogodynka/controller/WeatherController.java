package pl.sdacademy.pogodynka.controller;

import lombok.RequiredArgsConstructor;
import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.service.WeatherService;

@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException {
        return weatherService.getWeatherDataForCity(city);
    }

    public String getWeatherDataAsString(String city) throws WeatherNotFoundException {
        return weatherService.widgetText(city);
    }
}
