package pl.sdacademy.pogodynka.repository.api.openweathermap.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CurrentWeather {

    @JsonProperty("name")
    private String city;

    @JsonProperty("weather")
    private List<Weather> weatherList;

    @JsonProperty("main")
    private Temperature temperature;

    private Wind wind;

}
