package itgirls.wb.http.client;
import itgirls.wb.http.dto.WeatherDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class WeatherClient {
    private final RestTemplate restTemplate;
    private final String url;

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
 //       return restTemplate.getForObject(uri, WeatherDto.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Yandex-API-Key", "591c1a40-645e-4d3e-ba2e-a9415e8f95be");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, WeatherDto.class).getBody();
    }
}
