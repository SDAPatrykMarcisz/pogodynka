package pl.sdacademy.pogodynka.exceptions;

public class WeatherNotFoundException extends Exception {
    public WeatherNotFoundException(String city) {
        super(String.format("weather for city %s not found", city));
    }

    public WeatherNotFoundException(String lon, String lat) {
        super(String.format("weather for coordinates %s %s not found", lon, lat));
    }
}
