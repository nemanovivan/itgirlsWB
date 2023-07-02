package itgirls.wb.http.service;

import itgirls.wb.exceptions.NoCoordinatesFound;

import java.util.List;

public interface GeoLocatorService {

    /**
     * @param address - адрес, указанный пользователем
     * @return - лист чисел с плавающей точкой, состоящий из 2ух элементов:
     *           первый эелемент координата Longitude(индекс 0),
     *           второй - Latitude(индекс 1)
     * @throws NoCoordinatesFound - если адрес не был найден
     */
    List<Float> getCoordinates(String address) throws NoCoordinatesFound;


}
