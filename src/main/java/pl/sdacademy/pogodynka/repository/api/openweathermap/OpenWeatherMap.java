package pl.sdacademy.pogodynka.repository.api.openweathermap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.repository.api.openweathermap.model.dto.CurrentWeather;
import pl.sdacademy.pogodynka.repository.api.openweathermap.model.dto.Temperature;
import pl.sdacademy.pogodynka.repository.api.openweathermap.model.dto.Wind;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;
import pl.sdacademy.pogodynka.repository.api.WeatherClient;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Optional;

public class OpenWeatherMap implements WeatherClient {

    private String apiUrl;
    private WeatherMapCityRepository weatherMapCityRepository;

    public OpenWeatherMap() {
        this.apiUrl = "http://api.openweathermap.org";
        this.weatherMapCityRepository = WeatherMapCityRepository.getInstance();
    }

    OpenWeatherMap(String apiUrl, WeatherMapCityRepository weatherMapCityRepository) {
        this.apiUrl = apiUrl;
        this.weatherMapCityRepository = weatherMapCityRepository;
    }

    @Override
    public WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException {
        Optional<Long> cityId = weatherMapCityRepository.getIdOfCity(city);
        String queryParam = cityId.map(id -> String.format("id=%s", id)).orElse(String.format("q=%s", city));
        try {
            CurrentWeather currentWeather = downloadCurrentWeather(queryParam);
            return WeatherData.builder()
                    .city(currentWeather.getCity())
                    .description(getDescription(currentWeather))
                    .windSpeed(getWindSpeed(currentWeather))
                    .temperature(getTemperature(currentWeather))
                    .weatherGroup(getWeatherGroup(currentWeather))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeatherNotFoundException(city);
        }
    }

    private CurrentWeather downloadCurrentWeather(String... queryParams) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(getUri(queryParams))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CurrentWeather result = mapper.readValue(json, CurrentWeather.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new Exception("Weather not found");
    }

    private URI getUri(String[] queryParam) {
        String endpoint = "/data/2.5/weather?appid=1bf2280610892b23825a629aeb4cddc0&lang=pl&units=metric";
        StringBuilder url = new StringBuilder(apiUrl + endpoint);
        for (String str : queryParam) {
            url.append("&").append(str);
        }
        System.out.println(url.toString());
        return (URI.create(URLDecoder.decode(url.toString(), Charset.defaultCharset())));
    }

    private String getWeatherGroup(CurrentWeather currentWeather) {
        return Optional.ofNullable(currentWeather.getWeatherList())
                .filter(x -> x.size() > 0)
                .map(x -> x.get(0).getGroupOfWeather())
                .orElse("brak danych");
    }

    private String getDescription(CurrentWeather currentWeather) {
        return Optional.ofNullable(currentWeather.getWeatherList())
                .filter(x -> x.size() > 0)
                .map(x -> x.get(0).getDescription())
                .orElse("brak danych");
    }

    private Double getTemperature(CurrentWeather currentWeather) {
        return Optional.ofNullable(currentWeather.getTemperature())
                .map(Temperature::getTemperature)
                .orElse(Double.NaN);
    }

    private Double getWindSpeed(CurrentWeather currentWeather) {
        return Optional.ofNullable(currentWeather.getWind())
                .map(Wind::getSpeed)
                .orElse(Double.NaN);
    }


}
