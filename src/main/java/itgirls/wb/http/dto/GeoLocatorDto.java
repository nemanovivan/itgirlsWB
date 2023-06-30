package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GeoLocatorDto(ResponseDto response) {
    @JsonCreator
    public GeoLocatorDto(
            @JsonProperty("response") ResponseDto response
    ) {
        this.response = response;
    }

    public record ResponseDto(GeoObjectCollectionDto geoObjectCollection) {
        @JsonCreator
        public ResponseDto(
                @JsonProperty("GeoObjectCollection") GeoObjectCollectionDto geoObjectCollection
        ) {
            this.geoObjectCollection = geoObjectCollection;
        }
        public record GeoObjectCollectionDto(List<FeatureMemberDto> featuteMemberList) {
            @JsonCreator
            public GeoObjectCollectionDto(
                    @JsonProperty("featureMember") List<FeatureMemberDto> featuteMemberList
            ) {
                this.featuteMemberList = featuteMemberList;
            }
            public record FeatureMemberDto(GeoObjectDto geoObject) {
                @JsonCreator
                public FeatureMemberDto(
                        @JsonProperty("GeoObject") GeoObjectDto geoObject
                ) {
                    this.geoObject = geoObject;
                }
                public record GeoObjectDto(PointDto point) {
                    @JsonCreator
                    public GeoObjectDto(
                            @JsonProperty("Point") PointDto point
                    ) {
                        this.point = point;
                    }
                    public record PointDto(String coordinates) {
                        @JsonCreator
                        public PointDto(
                                @JsonProperty("pos") String coordinates
                        ) {
                            this.coordinates = coordinates;
                        }
                    }
                }
            }
        }
    }
}
