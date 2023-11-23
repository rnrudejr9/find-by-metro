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

    public HouseResponseDto toResponse(){
        StringBuilder markPosition = new StringBuilder();
        markPosition.append(roadName).append(" ");
        int pbonbun = Integer.parseInt(roadNameBonbun);
        int pbubun = Integer.parseInt(roadNameBubun);
        if(pbonbun != 0){
            markPosition.append(pbonbun);
        }
        if (pbubun != 0){
            markPosition.append("-").append(pbubun);
        }
        return HouseResponseDto.builder()
                .houseId(houseId)
                .buildYear(buildYear)
                .roadName(roadName)
                .roadNameBonbun(roadNameBonbun)
                .roadNameBubun(roadNameBubun)
                .roadNameCode(roadNameCode)
                .roadNameSigunguCode(roadNameSigunguCode)
                .dong(dong)
                .bonbun(bonbun)
                .bubun(bubun)
                .apartmentName(apartmentName)
                .jibun(jibun)
                .markPosition(markPosition.toString())
                .build();
    }
}