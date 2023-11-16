package com.ssafy.vue.house.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
@AllArgsConstructor
@Builder
public class HouseDealDto {
    private int housedealId;
    private String houseId;
    private String dealAmount;
    private double area;
    private String dealYear;
    private String dealMonth;
    private String dealDay;
    private String floor; 
}