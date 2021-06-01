package pl.sdacademy.pogodynka.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class WeatherData {

    private final String city;
    private final String weatherGroup;
    private final String description;
    private final Double temperature;
    private final Double windSpeed;

}
