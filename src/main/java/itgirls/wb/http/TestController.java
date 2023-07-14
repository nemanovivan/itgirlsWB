package itgirls.wb.http;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.client.WeatherClient;
import itgirls.wb.http.dto.WeatherDto;
import itgirls.wb.http.service.GeoLocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final GeoLocatorService geoLocatorService;
    private final WeatherClient weatherClient;

    @GetMapping("/coordinates")
    List<Float> getCoordinates(@RequestParam("address") String address) throws NoCoordinatesFound {
        return geoLocatorService.getCoordinates(address);
    }

    @GetMapping("/weather")
    WeatherDto getWeatherByCoordinates(@RequestParam("lat") float lat, @RequestParam("lon") float lon) {
        return weatherClient.getWeather(lat, lon);
    }

    @GetMapping("/weatherBy")
    WeatherDto getWeatherByAddtess(@RequestParam("address") String address)
            throws NoCoordinatesFound {
        float lat = geoLocatorService.getCoordinates(address).get(1);
        float lon = geoLocatorService.getCoordinates(address).get(0);
        return weatherClient.getWeather(lat, lon);
    }

    @GetMapping("/forecastBy")
    String getForecast(@RequestParam("address") String address)
            throws NoCoordinatesFound {
        return weatherClient.getWeather(address).toString();
    }
}
