package com.ssafy.vue.station.model.dto;

import lombok.*;

import java.util.Set;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StationResponseDto {
    private String name;
    private Double lat;
    private Double lng;
}
