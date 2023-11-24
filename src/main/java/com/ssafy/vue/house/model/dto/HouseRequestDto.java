package com.ssafy.vue.house.model.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HouseRequestDto {
    private String houseId;
    private String houseDealId;
}
