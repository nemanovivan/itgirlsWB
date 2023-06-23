package itgirls.wb.http;

import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.dto.GeoCodeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final GeoLocatorClient geoLocatorClient;
    @GetMapping("/geocoder")
    GeoCodeResponseDto getCoordinatesByAddress(@RequestParam("address") String address) {
        return geoLocatorClient.getCoordinates(address);
    }
}
