package itgirls.wb.http.service;

import itgirls.wb.exceptions.NoCoordinatesFound;

public interface GeoLocatorService {

    float getLatitute(String country, String city, String street, String numberOfHouse) throws NoCoordinatesFound;
    float getLongitude(String country, String city, String street, String numberOfHouse) throws NoCoordinatesFound ;

}
