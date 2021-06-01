package pl.sdacademy.pogodynka.controller;

import lombok.RequiredArgsConstructor;
import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.service.WeatherService;

import java.util.Collection;

public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController() {
        this.weatherService = new WeatherService();
    }

    public WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException {
        return weatherService.getWeatherDataForCity(city);
    }

    public String getWeatherDataAsString(String city) throws WeatherNotFoundException {
        return weatherService.widgetText(city);
    }

    public Collection<String> getCityNames(){
        return weatherService.getCityNames();
    }
}
