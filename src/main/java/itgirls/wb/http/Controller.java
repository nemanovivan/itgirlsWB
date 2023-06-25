package itgirls.wb.http;

import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.dto.Draft;
import itgirls.wb.http.dto.GeoLocatorDto;
import itgirls.wb.http.dto.ResponseDto;
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
}
