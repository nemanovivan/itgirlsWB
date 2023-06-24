package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureMemberDto {
    private final GeoObjectDto geoObject;
    @JsonCreator
    public FeatureMemberDto(
            @JsonProperty("GeoObject") GeoObjectDto geoObject
    ) {
        this.geoObject = geoObject;
    }
    public GeoObjectDto getGeoObject() {
        return geoObject;
    }
}
