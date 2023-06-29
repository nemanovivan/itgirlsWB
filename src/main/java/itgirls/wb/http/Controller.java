package itgirls.wb.http;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.client.WeatherClient;
import itgirls.wb.http.dto.GeoLocatorDto;
import itgirls.wb.http.dto.WeatherDto;
import itgirls.wb.http.service.GeoLocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final GeoLocatorService geoLocatorService;
    private final WeatherClient weatherClient;
    @GetMapping("/getLatitude")
    float getLatitudeByAddress(@RequestParam("country") String country,
                               @RequestParam("city") String city,
                               @RequestParam("street") String street,
                               @RequestParam("numberOfHouse") String numberOfHouse)
            throws NoCoordinatesFound {
        return geoLocatorService.getLatitute(country, city, street, numberOfHouse);
    }

    @GetMapping("/getLongitude")
    float getLongitudeByAddress(@RequestParam("country") String country,
                                @RequestParam("city") String city,
                                @RequestParam("street") String street,
                                @RequestParam("numberOfHouse") String numberOfHouse)
            throws NoCoordinatesFound {
        return geoLocatorService.getLongitude(country, city, street, numberOfHouse);
    }
    @GetMapping("/weather")
    WeatherDto getWeather(@RequestParam("lat") float lat, @RequestParam("lon") float lon) {
        return  weatherClient.getWeather(lat, lon);
    }
    @GetMapping("/weatherBy")
    WeatherDto getWeatherByCoordinates (@RequestParam("country") String country,
                                        @RequestParam("city") String city,
                                        @RequestParam("street") String street,
                                        @RequestParam("numberOfHouse") String numberOfHouse)
    throws NoCoordinatesFound {
        float lat = geoLocatorService.getLatitute(country, city, street, numberOfHouse);
        float lon = geoLocatorService.getLongitude(country, city, street, numberOfHouse);
        return weatherClient.getWeather(lat, lon);
    }
}