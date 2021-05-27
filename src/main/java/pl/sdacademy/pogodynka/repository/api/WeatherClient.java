package pl.sdacademy.pogodynka.repository.api;

import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;

public interface WeatherClient {

    WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException;

}
