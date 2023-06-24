package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoObjectDto {
    private final PointDto point;
    @JsonCreator
    public GeoObjectDto(
            @JsonProperty("Point") PointDto point
    ) {
        this.point = point;
    }

    public PointDto getPoint() {
        return point;
    }
}
