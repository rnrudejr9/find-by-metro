package com.ssafy.vue.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class HouseSetupResponse {
    private HashMap<String,HouseDto> houseDtoList = new HashMap<>();
    private List<HouseDealDto> houseDealDtoList = new ArrayList<>();
}
