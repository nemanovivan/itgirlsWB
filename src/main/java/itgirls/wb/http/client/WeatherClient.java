package itgirls.wb.http.client;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.dto.WeatherDto;
import itgirls.wb.http.service.GeoLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class WeatherClient {
    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    private GeoLocatorService geoLocatorService;


    @Value("${apikeyWeather}")
    private String apiKeyWeather;

    public WeatherClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }


    public WeatherDto getWeather(float lat, float lon) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .pathSegment("informers")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("format", "json")
                .build()
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Yandex-API-Key", apiKeyWeather);
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, WeatherDto.class).getBody();
    }

    public WeatherDto getWeather(String country, String city, String street, String numberOfHouse) throws NoCoordinatesFound {
        List<Float> coordinates = geoLocatorService.getCoordinates(country, city, street, numberOfHouse);
        return getWeather(coordinates.get(1), coordinates.get(0));
    }
}
