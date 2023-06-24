package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDto {
    private final GeoObjectCollectionDto geoObjectCollection;
    @JsonCreator
    public ResponseDto(
            @JsonProperty("GeoObjectCollection") GeoObjectCollectionDto geoObjectCollection
    ) {
        this.geoObjectCollection = geoObjectCollection;
    }

    public GeoObjectCollectionDto getGeoObjectCollection() {
        return geoObjectCollection;
    }
}
