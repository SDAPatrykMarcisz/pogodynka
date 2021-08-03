package pl.sdacademy.pogodynka.service;

import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.external.api.location.LocationByIpApi;
import pl.sdacademy.pogodynka.model.dto.Coordinates;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.external.api.weather.openweather.OpenWeatherApi;
import pl.sdacademy.pogodynka.external.api.weather.WeatherClient;
import pl.sdacademy.pogodynka.repository.WeatherDatabaseClient;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;

import java.util.Collection;

public class WeatherService {

    private WeatherClient weatherClient;
    private WeatherDatabaseClient weatherDatabase;
    private LocationByIpApi locationByIpApi;

    public WeatherService() {
        this.weatherClient = new OpenWeatherApi();
        this.weatherDatabase = new WeatherMapCityRepository();
        this.locationByIpApi = new LocationByIpApi();
    }

    WeatherService(WeatherClient weatherClient, WeatherDatabaseClient weatherDatabase, LocationByIpApi locationByIpApi) {
        this.weatherClient = weatherClient;
        this.weatherDatabase = weatherDatabase;
        this.locationByIpApi = locationByIpApi;
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

    public WeatherData getWeatherDataForCoords(String lon, String lat) throws WeatherNotFoundException {
        return getWeatherDataForLocation(new Coordinates(lon, lat));
    }

    public WeatherData getWeatherDataForCurrentLocation() throws WeatherNotFoundException {
        return getWeatherDataForLocation(locationByIpApi.getCoordinatesForCurrentDevice());
    }

    private WeatherData getWeatherDataForLocation(Coordinates coords) throws WeatherNotFoundException {
        return weatherClient.getWeatherDataForCoordinates(coords);
    }
}
