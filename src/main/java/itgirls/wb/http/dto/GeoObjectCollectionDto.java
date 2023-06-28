package itgirls.wb.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeoObjectCollectionDto {
    private final List<FeatureMemberDto> featuteMemberList;
    @JsonCreator
    public GeoObjectCollectionDto(
            @JsonProperty("featureMember") List<FeatureMemberDto> featuteMemberList
    ) {
        this.featuteMemberList = featuteMemberList;
    }

    public List<FeatureMemberDto> getFeatuteMemberList() {
        return featuteMemberList;
    }
}
