package itgirls.wb.http.service;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.dto.GeoLocatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoLocatorServiceImpl implements GeoLocatorService {
    private final GeoLocatorClient geoLocatorClient;

    @Override
    public float getLatitute(String country, String city, String street, String numberOfHouse) throws NoCoordinatesFound {
        String address = combineAddress(country, city, street, numberOfHouse);
        try {
            GeoLocatorDto geoLocatorDto = geoLocatorClient.getCoordinates(address);
            String coordinates = getCoordinatesFromDto(geoLocatorDto);
            String lat = coordinates.substring(0, coordinates.indexOf(' '));
            float result = Float.valueOf(lat);
            return result;
        } catch (Exception e) {
            throw new NoCoordinatesFound("Для данного адреса не найдены координаты!");
        }
    }

    @Override
    public float getLongitude(String country, String city, String street, String numberOfHouse) throws NoCoordinatesFound {
        String address = combineAddress(country, city, street, numberOfHouse);
        try {
            GeoLocatorDto geoLocatorDto = geoLocatorClient.getCoordinates(address);
            String coordinates = getCoordinatesFromDto(geoLocatorDto);
            String lon = coordinates.substring(coordinates.indexOf(' ') + 1);
            float result = Float.valueOf(lon);
            return result;
        } catch (Exception e) {
            throw new NoCoordinatesFound("Для данного адреса не найдены координаты!");
        }
    }

    private String getCoordinatesFromDto(GeoLocatorDto geoLocatorDto) {
        return geoLocatorDto.getResponse()
                .getGeoObjectCollection()
                .getFeatuteMemberList().get(0)
                .getGeoObject()
                .getPoint()
                .getCoordinates();
    }

    private String combineAddress(String country, String city, String street, String numberOfHouse){
        return country +" "+city+" "+street+" "+numberOfHouse;
    }
}
