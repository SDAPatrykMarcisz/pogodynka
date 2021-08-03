package pl.sdacademy.pogodynka.controller;

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

    public WeatherData getWeatherForCoordinates(String lon, String lat) throws WeatherNotFoundException {
        return weatherService.getWeatherDataForCoords(lon, lat);
    }

    public WeatherData getWeatherForCurrentLocation() throws WeatherNotFoundException {
        return weatherService.getWeatherDataForCurrentLocation();
    }

    public String getWeatherDataAsString(String city) throws WeatherNotFoundException {
        return weatherService.widgetText(city);
    }

    public Collection<String> getCityNames(){
        return weatherService.getCityNames();
    }
}
