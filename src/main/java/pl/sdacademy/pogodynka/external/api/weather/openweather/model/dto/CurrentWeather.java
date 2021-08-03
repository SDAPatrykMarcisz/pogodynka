package pl.sdacademy.pogodynka.external.api.weather.openweather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.sdacademy.pogodynka.model.dto.Coordinates;

import java.util.List;

@Data
public class CurrentWeather {

    @JsonProperty("coord")
    private Coordinates coordinates;

    @JsonProperty("name")
    private String city;

    @JsonProperty("weather")
    private List<Weather> weatherList;

    @JsonProperty("main")
    private WeatherSummary weatherSummary;

    @JsonProperty("wind")
    private Wind wind;

}
