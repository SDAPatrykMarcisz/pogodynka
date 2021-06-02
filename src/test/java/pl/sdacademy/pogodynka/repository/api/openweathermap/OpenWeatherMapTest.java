package pl.sdacademy.pogodynka.repository.api.openweathermap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
import pl.sdacademy.pogodynka.repository.WeatherMapCityRepository;
import pl.sdacademy.pogodynka.repository.api.WeatherClient;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OpenWeatherMapTest {

    private WeatherClient client;
    private HttpClient httpClient;

    @Mock
    private WeatherMapCityRepository repository;

    @BeforeEach
    void setUp() {
        httpClient = Mockito.mock(HttpClient.class);
        client = new OpenWeatherMap("http://api.openweathermap.org", repository, httpClient);
    }

    @Test
    void shouldHaveIdInUrlWhenIdOfCityExists() throws WeatherNotFoundException, IOException, InterruptedException {
        //given
        Mockito.when(repository.getIdOfCity(any())).thenReturn(Optional.of(12345L));

        HttpResponse response = new MockResponse();
        Mockito.when(httpClient.send(any(), any())).thenReturn(response);

        //when
        client.getWeatherDataForCity("Ateny");

        //then
        ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        Mockito.verify(httpClient).send(argumentCaptor.capture(), any());

        assertTrue(argumentCaptor.getValue().uri().toString().contains("id=12345"));
    }

    @Test
    void shouldHaveQinUrlWhenIdOfCityNotExists() throws IOException, InterruptedException, WeatherNotFoundException {
        //given
        Mockito.when(repository.getIdOfCity(any())).thenReturn(Optional.empty());
        HttpResponse response = new MockResponse();
        Mockito.when(httpClient.send(any(), any())).thenReturn(response);

        String name = "Ateny";
        //when
        client.getWeatherDataForCity(name);

        //then
        ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        Mockito.verify(httpClient).send(argumentCaptor.capture(), any());

        assertTrue(argumentCaptor.getValue().uri().toString().contains("q=" + name));
    }

    @Test
    void shouldHandleHttpClientException() throws IOException, InterruptedException {
        //given
        Mockito.when(httpClient.send(any(), any())).thenThrow(new IOException("communication error"));

        WeatherNotFoundException exception = assertThrows(WeatherNotFoundException.class, () -> {
            //when
            client.getWeatherDataForCity("city");
        });

        //then
        assertEquals("weather for city city not found", exception.getMessage());
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsForPalindromes() {
        Map<String, Long> input = Map.of(
                "Ateny", 1L,
                "Krakow", 2L,
                "Moskwa", 3L,
                "Barcelona", 4L
        );
        return input.entrySet().stream()
                .map(params -> dynamicTest(
                        String.format("should convert %s to %s in url", params.getKey(), params.getValue()),
                        () -> {
                            //given
                            Mockito.when(repository.getIdOfCity(any())).thenReturn(Optional.of(params.getValue()));

                            HttpResponse response = new MockResponse();
                            Mockito.when(httpClient.send(any(), any())).thenReturn(response);

                            //when
                            client.getWeatherDataForCity(params.getKey());

                            //then
                            ArgumentCaptor<HttpRequest> argumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
                            Mockito.verify(httpClient).send(argumentCaptor.capture(), any());

                            assertTrue(argumentCaptor.getValue().uri().toString().contains("id=" + params.getValue()));
                            Mockito.reset(repository, httpClient);
                        }
                ));
    }

    @Test
    void parametrizedShouldHaveIdInUrlWhenIdOfCityExists() {

    }

    static class MockResponse implements HttpResponse<String> {

        @Override
        public int statusCode() {
            return 200;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public String body() {
            return "{}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return null;
        }

        @Override
        public HttpClient.Version version() {
            return null;
        }
    }

}