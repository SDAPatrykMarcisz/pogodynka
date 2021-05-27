package pl.sdacademy.pogodynka.repository.api.openweathermap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.model.dto.WeatherData;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;
import pl.sdacademy.pogodynka.utils.ReadFileToStringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockitoExtension.class)
public class WeatherClientTest {

    private OpenWeatherMap weatherClient;
    private static ClientAndServer mockServer;

    @Mock
    private WeatherMapCityRepository weatherMapCityRepository;

    @BeforeEach
    void setUp() {
        weatherClient = new OpenWeatherMap("http://localhost:8080", weatherMapCityRepository);
        mockServer.reset();
    }

    @BeforeAll
    public static void startServer() {
        mockServer = startClientAndServer(8080);
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
    }

    @Test
    void shouldParseJsonResponse() throws WeatherNotFoundException {
        //GIVEN
        createResponseForWeatherApp();
        //WHEN
        WeatherData currentWeather = weatherClient.getWeatherDataForCity("Warszawa");
        //THEN
        assertEquals("Warszawa", currentWeather.getCity());
        assertEquals("Clouds", currentWeather.getWeatherGroup());
        assertEquals("lekkie zachmurzenie", currentWeather.getDescription());
        assertEquals(15.98, currentWeather.getTemperature());
        assertEquals(0.45, currentWeather.getWindSpeed());
    }

    @Test
    void shouldUseIdInRequestIfCityInDatabase() throws WeatherNotFoundException {
        //given
        String warsawCurrentResponse = ReadFileToStringUtils.getFileFromResourceAsString("current_weather_api/current_for_warsaw.json");
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/data/2.5/weather")
                        .withQueryStringParameter("appid, 1bf2280610892b23825a629aeb4cddc0")
                        .withQueryStringParameter("lang", "pl")
                        .withQueryStringParameter("units", "metric")
                        .withQueryStringParameter("id", "12345")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withHeader("Content-type", "application/json")
                        .withBody(warsawCurrentResponse)
                        .withDelay(TimeUnit.SECONDS, 1)
        );

        when(weatherMapCityRepository.getIdOfCity("Warszawa")).thenReturn(Optional.of(12345L));

        //when
        WeatherData currentWeather = weatherClient.getWeatherDataForCity("Warszawa");

        //then
        assertEquals("Warszawa", currentWeather.getCity());
        assertEquals("Clouds", currentWeather.getWeatherGroup());
        assertEquals("lekkie zachmurzenie", currentWeather.getDescription());
        assertEquals(15.98, currentWeather.getTemperature());
        assertEquals(0.45, currentWeather.getWindSpeed());

    }

    private void createResponseForWeatherApp() {
        String warsawCurrentResponse = ReadFileToStringUtils.getFileFromResourceAsString("current_weather_api/current_for_warsaw.json");

        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/data/2.5/weather")
                        .withQueryStringParameter("appid, 1bf2280610892b23825a629aeb4cddc0")
                        .withQueryStringParameter("lang", "pl")
                        .withQueryStringParameter("units", "metric")
                        .withQueryStringParameter("q", "Warszawa")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withHeader("Content-type", "application/json")
                        .withBody(warsawCurrentResponse)
                        .withDelay(TimeUnit.SECONDS, 1)
        );
    }

}