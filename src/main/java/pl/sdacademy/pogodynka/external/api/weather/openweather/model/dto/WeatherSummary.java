package pl.sdacademy.pogodynka.external.api.weather.openweather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherSummary {

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double windChill;

    @JsonProperty("temp_min")
    private Double minimal;

    @JsonProperty("temp_max")
    private Double maximal;

    @JsonProperty("pressure")
    private Double pressure;

    @JsonProperty("humidity")
    private Double humidity;

}
