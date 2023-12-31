package com.ssafy.vue.station.model.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Station {
    private String name;
    private String code;
    private Set<String> line;
    private Double lat;
    private Double lng;
    private Set<Station> connectStation;
    private Set<String> dong;
    public StationResponseDto toDto(){
        return StationResponseDto.builder()
                .name(name)
                .lat(lat)
                .lng(lng)
                .build();
    }
}
