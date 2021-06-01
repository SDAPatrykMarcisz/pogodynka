package pl.sdacademy.pogodynka.repository.api.openweathermap.model.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Table(name = "weather_city")
@Entity
@Data
public class WeatherMapCityEntity {

    @Id
    private Long id;

    private String country;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keyWords;

}
