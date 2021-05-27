package pl.sdacademy.pogodynka.repository.api.openweathermap.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {

    @JsonProperty("main")
    private String groupOfWeather;

    private String description;

}
