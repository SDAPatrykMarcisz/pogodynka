package pl.sdacademy.pogodynka.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherData {

    private final String city;
    private final String weatherGroup;
    private final String description;
    private final Double temperature;
    private final Double windSpeed;

}
