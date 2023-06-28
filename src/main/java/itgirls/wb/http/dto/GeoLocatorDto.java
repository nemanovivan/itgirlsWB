package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoLocatorDto {
    private final ResponseDto response;
    @JsonCreator
    public GeoLocatorDto(
            @JsonProperty("response") ResponseDto response
    ) {
        this.response = response;
    }

    public ResponseDto getResponse() {
        return response;
    }
}
