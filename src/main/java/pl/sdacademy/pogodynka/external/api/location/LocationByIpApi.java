package pl.sdacademy.pogodynka.external.api.location;

import pl.sdacademy.pogodynka.external.api.location.ipapi.model.IdApiResponse;
import pl.sdacademy.pogodynka.external.api.webclient.HttpWebClient;
import pl.sdacademy.pogodynka.model.dto.Coordinates;

public class LocationByIpApi extends HttpWebClient {

    private final String apiUrl;

    public LocationByIpApi() {
        this.apiUrl = "http://ip-api.com/json/";
    }

    public LocationByIpApi(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Coordinates getCoordinatesForCurrentDevice(){
        try {
            IdApiResponse idApiResponse = executeGet(apiUrl, IdApiResponse.class);
            return idApiResponse.getCoordinates();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("coordinates for current location not found");
    }

}
