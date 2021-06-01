package pl.sdacademy.pogodynka.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.sdacademy.pogodynka.integration.model.jsonsource.WeatherMapCity;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;
import pl.sdacademy.pogodynka.repository.api.openweathermap.model.dao.WeatherMapCityEntity;
import pl.sdacademy.pogodynka.utils.ReadFileToStringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MainDatabase {

    public static void main(String[] args) throws JsonProcessingException {
        String fileFromResourceAsString = ReadFileToStringUtils.getFileFromResourceAsString("current.city.list.json");
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<WeatherMapCity> citiesInJson = mapper.readValue(fileFromResourceAsString, new TypeReference<>() {});

        WeatherMapCityRepository.getInstance().saveToDatabase(citiesInJson.stream()
                .map(city -> {
                    WeatherMapCityEntity weatherMapCityEntity = new WeatherMapCityEntity();
                    weatherMapCityEntity.setId(city.getId());
                    weatherMapCityEntity.setCountry(city.getCountry());
                    Set<String> keyWords = Optional.ofNullable(city.getLangs()).map(langs -> langs.stream()
                            .flatMap(x -> x.values().stream())
                            .collect(Collectors.toSet())).orElse(new HashSet<>());
                    keyWords.add(city.getName());
                    weatherMapCityEntity.setKeyWords(keyWords);
                    return weatherMapCityEntity;
                }).collect(Collectors.toList()));
    }
}
