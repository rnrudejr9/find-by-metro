package com.ssafy.vue.house.model.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class House {
    private double 거래금액;
    private String 거래유형;
    private int 건축년도;
    private String 년;
    private String 도로명;
    private String 도로명건물본번호코드;
    private String 도로명건물부번호코드;
    private String 도로명시군구코드;
    private String 도로명일련번호코드;
    private String 도로명지상지하코드;
    private String 도로명코드;
    private String 등기일자;
    private String 법정동;
    private String 법정동본번코드;
    private String 법정동부번코드;
    private String 법정동시군구코드;
    private String 법정동읍면동코드;
    private String 법정동지번코드;
    private String 아파트;
    private String 월;
    private String 일;
    private String 일련번호;
    private double 전용면적;
    private String 중개사소재지;
    private String 지번;
    private String 지역코드;
    private String 층;
    private String 해제사유발생일;
    private String 해제여부;
}
