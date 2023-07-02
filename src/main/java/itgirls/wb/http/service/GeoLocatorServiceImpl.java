package itgirls.wb.http.service;

import itgirls.wb.exceptions.NoCoordinatesFound;
import itgirls.wb.http.client.GeoLocatorClient;
import itgirls.wb.http.dto.GeoLocatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoLocatorServiceImpl implements GeoLocatorService {
    private final GeoLocatorClient geoLocatorClient;

    /**
     * @param country - страна, указанная пользователем
     * @param city - город, указанный пользователем
     * @param street - улица, указанная пользователем
     * @param numberOfHouse - номер дома, указанный пользователем
     * @return - лист чисел с плавающей точкой, состоящий из 2ух элементов:
     *           первый эелемент координата Longitude(индекс 0),
     *           второй - Latitude(индекс 1)
     * @throws NoCoordinatesFound
     */
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

    private String combineAddress(String country, String city, String street, String numberOfHouse) {
        return country + " " + city + " " + street + " " + numberOfHouse;
    }
}