package itgirls.wb.http.service;

import itgirls.wb.exceptions.NoCoordinatesFound;

import java.util.List;

public interface GeoLocatorService {

    List<Float> getCoordinates(String address) throws NoCoordinatesFound;


}
