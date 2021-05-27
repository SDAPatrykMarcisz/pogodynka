package pl.sdacademy.pogodynka.repository.api.openweathermap.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Temperature {

    @JsonProperty("temp")
    private Double temperature;

}
