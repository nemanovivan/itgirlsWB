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
public class Controller {
    private final GeoLocatorService geoLocatorService;
    private final WeatherClient weatherClient;

    @GetMapping("/getCoordinates")
    List<Float> getCoordinatesByAddress(@RequestParam("country") String country,
                                     @RequestParam("city") String city,
                                     @RequestParam("street") String street,
                                     @RequestParam("numberOfHouse") String numberOfHouse)
            throws NoCoordinatesFound {
        return geoLocatorService.getCoordinates(country, city, street, numberOfHouse);
    }

    @GetMapping("/weather")
    WeatherDto getWeather(@RequestParam("lat") float lat, @RequestParam("lon") float lon) {
        return weatherClient.getWeather(lat, lon);
    }

    @GetMapping("/weatherBy")
    WeatherDto getWeatherByCoordinates(@RequestParam("country") String country,
                                       @RequestParam("city") String city,
                                       @RequestParam("street") String street,
                                       @RequestParam("numberOfHouse") String numberOfHouse)
            throws NoCoordinatesFound {
        return weatherClient.getWeather(country, city, street, numberOfHouse);
    }
}