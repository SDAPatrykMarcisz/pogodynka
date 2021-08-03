package pl.sdacademy.pogodynka.external.api.weather.openweather;

import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.external.api.weather.WeatherClient;
import pl.sdacademy.pogodynka.external.api.weather.openweather.model.dto.CurrentWeather;
import pl.sdacademy.pogodynka.external.api.weather.openweather.model.dto.WeatherSummary;
import pl.sdacademy.pogodynka.external.api.weather.openweather.model.dto.Wind;
import pl.sdacademy.pogodynka.external.api.webclient.HttpWebClient;
import pl.sdacademy.pogodynka.model.dto.Coordinates;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;

import java.util.Optional;

public class OpenWeatherApi extends HttpWebClient implements WeatherClient {

    private final String apiUrl;
    private final WeatherMapCityRepository weatherMapCityRepository;


    public OpenWeatherApi() {
        this.apiUrl = "http://api.openweathermap.org";
        this.weatherMapCityRepository = WeatherMapCityRepository.getInstance();
    }

    OpenWeatherApi(String apiUrl, WeatherMapCityRepository weatherMapCityRepository) {
        this.apiUrl = apiUrl;
        this.weatherMapCityRepository = weatherMapCityRepository;
    }

    @Override
    public WeatherData getWeatherDataForCity(String city) throws WeatherNotFoundException {
        Optional<Long> cityId = weatherMapCityRepository.getIdOfCity(city);
        String queryParam = cityId.map(id -> String.format("id=%s", id))
                .orElse(String.format("q=%s", city));
        try {
            String url = apiUrl + "/data/2.5/weather?appid=1bf2280610892b23825a629aeb4cddc0&lang=pl&units=metric";
            CurrentWeather currentWeather = executeGet(url, CurrentWeather.class, queryParam);
            return convert(currentWeather);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeatherNotFoundException(city);
        }
    }

    @Override
    public WeatherData getWeatherDataForCoordinates(Coordinates coords) throws WeatherNotFoundException {
        try {
            String url = apiUrl + "/data/2.5/weather?appid=1bf2280610892b23825a629aeb4cddc0&lang=pl&units=metric";
            CurrentWeather currentWeather = executeGet(
                    url,
                    CurrentWeather.class,
                    String.format("lon=%s", coords.getLon()),
                    String.format("lat=%s", coords.getLat())
            );
            return convert(currentWeather);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WeatherNotFoundException(coords.getLon(), coords.getLat());
        }
    }

    private WeatherData convert(CurrentWeather currentWeather) {
        return WeatherData.builder()
                .city(currentWeather.getCity())
                .description(getDescription(currentWeather))
                .windSpeed(getWindSpeed(currentWeather))
                .temperature(getTemperature(currentWeather))
                .weatherGroup(getWeatherGroup(currentWeather))
                .build();
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
        return Optional.ofNullable(currentWeather.getWeatherSummary())
                .map(WeatherSummary::getTemperature)
                .orElse(Double.NaN);
    }

    private Double getWindSpeed(CurrentWeather currentWeather) {
        return Optional.ofNullable(currentWeather.getWind())
                .map(Wind::getSpeed)
                .orElse(Double.NaN);
    }

}
