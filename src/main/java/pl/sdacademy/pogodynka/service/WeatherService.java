package pl.sdacademy.pogodynka.service;

import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.repository.WeatherDatabaseClient;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;
import pl.sdacademy.pogodynka.repository.api.openweathermap.OpenWeatherMap;
import pl.sdacademy.pogodynka.repository.api.WeatherClient;

import java.util.Collection;

public class WeatherService {

    private WeatherClient weatherClient;
    private WeatherDatabaseClient weatherDatabase;

    public WeatherService() {
        this.weatherClient = new OpenWeatherMap();
        this.weatherDatabase = new WeatherMapCityRepository();
    }

    public WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException {
        return weatherClient.getWeatherDataForCity(city);
    }

    public String widgetText(String city) throws WeatherNotFoundException {
        WeatherData weatherData = weatherClient.getWeatherDataForCity(city);
        StringBuilder sb = new StringBuilder();
        sb.append("Aktualna pogoda dla: ").append(weatherData.getCity()).append("\n")
                .append("Stan pogody: ").append(weatherData.getWeatherGroup())
                .append(", ").append(weatherData.getDescription()).append("\n")
                .append("Temperatura: ").append(weatherData.getTemperature()).append(" \u2103").append(" ").append("\n")
                .append("Prędkość wiatru: ").append(weatherData.getWindSpeed()).append(" m/s");
        return sb.toString();
    }

    public Collection<String> getCityNames() {
        return weatherDatabase.getCityList();
    }
}
