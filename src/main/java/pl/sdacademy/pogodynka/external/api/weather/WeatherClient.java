package pl.sdacademy.pogodynka.external.api.weather;

import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.Coordinates;
import pl.sdacademy.pogodynka.model.dto.WeatherData;

public interface WeatherClient {

    WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException;

    WeatherData getWeatherDataForCoordinates(Coordinates coords) throws WeatherNotFoundException;
}
