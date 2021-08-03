//package pl.sdacademy.pogodynka.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pl.sdacademy.pogodynka.exceptions.WeatherNotFoundException;
//import pl.sdacademy.pogodynka.model.dto.WeatherData;
//import pl.sdacademy.pogodynka.repository.api.WeatherClient;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class WeatherServiceTest {
//
//    private WeatherService weatherService;
//
//    @Mock
//    WeatherClient weatherClient;
//
//    @BeforeEach
//    void setUp(){
//        weatherService = new WeatherService(weatherClient);
//    }
//
//    @Test
//    void shouldConvertWeatherDataToString() throws WeatherNotFoundException {
//        WeatherData dataToReturn = WeatherData.builder()
//                .city("Ateny")
//                .temperature(20.0)
//                .description("tak se jest")
//                .windSpeed(40.0)
//                .weatherGroup("chmurzyscie")
//                .build();
//
//        when(weatherClient.getWeatherDataForCity("Ateny")).thenReturn(dataToReturn);
//        String ateny = weatherService.widgetText("Ateny");
//
//        assertEquals("Aktualna pogoda dla: Ateny\n" +
//                "Stan pogody: chmurzyscie, tak se jest\n" +
//                "Temperatura: 20.0 ℃ \n" +
//                "Prędkość wiatru: 40.0 m/s", ateny);
//    }
//
//}