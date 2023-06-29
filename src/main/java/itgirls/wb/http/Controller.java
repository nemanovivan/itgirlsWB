package itgirls.wb.http;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.service.GeoLocatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class Controller {
    private final GeoLocatorService geoLocatorService;
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
}
