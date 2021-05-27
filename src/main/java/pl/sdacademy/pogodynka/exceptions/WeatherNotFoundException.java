package pl.sdacademy.pogodynka.exceptions;

public class WeatherNotFoundException extends Exception {
    public WeatherNotFoundException(String city) {
        super(String.format("weather for city %s not found", city));
    }
}
