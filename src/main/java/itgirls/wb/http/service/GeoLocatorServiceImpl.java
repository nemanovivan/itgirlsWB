package itgirls.wb.http.service;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.dto.GeoLocatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoLocatorServiceImpl implements GeoLocatorService {
    private final GeoLocatorClient geoLocatorClient;

    @Cacheable("coordinatesCache")
    @Override
    public List<Float> getCoordinates(String address) throws NoCoordinatesFound {
        try {
            GeoLocatorDto geoLocatorDto = geoLocatorClient.getCoordinatesFrom(address);
            String[] coordinates = getCoordinatesFromDto(geoLocatorDto).split(" ");
            return Arrays.stream(coordinates).map(Float::valueOf).toList();
        } catch (Exception e) {
            throw new NoCoordinatesFound("Для данного адреса не найдены координаты!");
        }
    }

    private String getCoordinatesFromDto(GeoLocatorDto geoLocatorDto) {
        return geoLocatorDto.response()
                .geoObjectCollection()
                .featuteMemberList().get(0)
                .geoObject()
                .point()
                .coordinates();
    }

}