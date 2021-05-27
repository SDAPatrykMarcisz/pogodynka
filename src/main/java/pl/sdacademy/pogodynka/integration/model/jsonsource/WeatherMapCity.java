package pl.sdacademy.pogodynka.integration.model.jsonsource;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WeatherMapCity {
    private Long id;
    private String name;
    private List<Map<String, String>> langs;
}
