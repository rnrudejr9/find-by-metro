package com.ssafy.vue.house.model.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class HouseDto {
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
}