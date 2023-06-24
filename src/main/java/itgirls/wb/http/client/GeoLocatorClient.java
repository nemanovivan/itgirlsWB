package itgirls.wb.http.client;

import itgirls.wb.http.dto.Draft;
import itgirls.wb.http.dto.GeoLocatorDto;
import itgirls.wb.http.dto.ResponseDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class GeoLocatorClient {
    private final RestTemplate restTemplate;
    private final String url;

    public GeoLocatorClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public GeoLocatorDto getCoordinates(String geocode){
        URI uri = UriComponentsBuilder.fromUriString(url)
                //.queryParam("apikey", "${apikeyGeoCoder}")//можно ли так ??
                .queryParam("apikey", "184145a8-e7ea-426a-8bda-86de1e552106")
                .queryParam("geocode", geocode)
                .queryParam("format", "json")
                .build()
                .toUri();
        return restTemplate.getForObject(uri, GeoLocatorDto.class);
    }
}
