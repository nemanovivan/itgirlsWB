package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PointDto {
    private final String coordinates;
    @JsonCreator
    public PointDto(
            @JsonProperty("pos") String coordinates
    ) {
        this.coordinates = coordinates;
    }

    public String getCoordinates() {
        return coordinates;
    }
}
