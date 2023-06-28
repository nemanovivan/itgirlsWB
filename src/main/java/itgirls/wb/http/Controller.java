package itgirls.wb.http;

import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.client.WeatherClient;
import itgirls.wb.http.dto.GeoLocatorDto;
import itgirls.wb.http.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
 private final GeoLocatorClient geoLocatorClient;
    @GetMapping("/geocoder")
    GeoLocatorDto getCoordinatesByAddress(@RequestParam("address") String address) {
        return geoLocatorClient.getCoordinates(address);
    }
    private final WeatherClient weatherClient;
    @GetMapping("/weather")
    WeatherDto getWeatherByCoordinates(@RequestParam("lat") float lat, @RequestParam("lon") float lon) {
        return  weatherClient.getWeather(lat, lon);
    }
}