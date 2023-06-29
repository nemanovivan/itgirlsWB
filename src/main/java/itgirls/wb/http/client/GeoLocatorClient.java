package itgirls.wb.http.client;

import itgirls.wb.http.dto.GeoLocatorDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class GeoLocatorClient {
    private final RestTemplate restTemplate;
    private final String url;

    private final String language = "ru_RU";

    static final int AMOUNT_OF_RESULTS = 1;

    @Value("${apikeyGeoCoder}")
    private String apiKey;

    public GeoLocatorClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public GeoLocatorDto getCoordinates(String address) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("apikey", apiKey)
                .queryParam("geocode", address)
                .queryParam("format", "json")
                .queryParam("results", AMOUNT_OF_RESULTS)
                .queryParam("lang", language)
                .build()
                .toUri();
        GeoLocatorDto coordinates = restTemplate.getForObject(uri, GeoLocatorDto.class);

        return restTemplate.getForObject(uri, GeoLocatorDto.class);
    }
}
