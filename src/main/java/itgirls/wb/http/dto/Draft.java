package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//public record Draft(ResponseDto response)
public record Draft() {
    /*
    @JsonCreator
    public Draft(
            @JsonProperty("response") ResponseDto response
    ) {
        this.response = response;
    }

    public record ResponseDto(GeoObjectCollectionDto geoObjectCollection){
        @JsonCreator
        public ResponseDto(
                @JsonProperty("GeoObjectCollectionDto") GeoObjectCollectionDto geoObjectCollection
        ) {
            this.geoObjectCollection = geoObjectCollection;
        }

        public record GeoObjectCollectionDto(List<FeatureMemberDto> featureMemberList){
            @JsonCreator
            public GeoObjectCollectionDto(
                    @JsonProperty
            )
        }
    }*/
}


