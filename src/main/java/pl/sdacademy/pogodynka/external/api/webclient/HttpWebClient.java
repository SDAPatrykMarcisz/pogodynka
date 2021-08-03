package pl.sdacademy.pogodynka.external.api.webclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

public class HttpWebClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpWebClient() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    HttpWebClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }


    protected <T> T executeGet(String baseUrl, Class<T> responseBodyType, String... queryParams) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(createUri(baseUrl, queryParams))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseBodyType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new Exception("Weather not found");
    }

    protected URI createUri(String baseUrl, String[] queryParam) {
        StringBuilder url = new StringBuilder(baseUrl);
        for (String str : queryParam) {
            url.append("&").append(str);
        }
        return (URI.create(URLDecoder.decode(url.toString(), Charset.defaultCharset()).replaceAll(" ", "%20")));
    }

}
