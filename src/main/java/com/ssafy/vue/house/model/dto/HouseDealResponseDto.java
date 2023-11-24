package com.ssafy.vue.house.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class HouseDealResponseDto {
    private String houseId;
    private int buildYear;
    private String roadName;
    private String roadNameBonbun;
    private String roadNameBubun;
    private String roadNameSigunguCode;
    private String roadNameCode;
    private String dong;
    private String bonbun;
    private String bubun;
    private String apartmentName;
    private String jibun;
    private String markPosition;
}
