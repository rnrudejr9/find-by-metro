package com.ssafy.vue.house.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class HouseResponseDto {
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
