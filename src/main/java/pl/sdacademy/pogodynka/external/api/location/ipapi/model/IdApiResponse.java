package pl.sdacademy.pogodynka.external.api.location.ipapi.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import pl.sdacademy.pogodynka.model.dto.Coordinates;

@Data
public class IdApiResponse {

    @JsonUnwrapped
    private Coordinates coordinates;

}

