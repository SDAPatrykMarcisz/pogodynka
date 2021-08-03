package pl.sdacademy.pogodynka.external.api.weather.openweather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {

    @JsonProperty("main")
    private String groupOfWeather;

    private String description;

}
