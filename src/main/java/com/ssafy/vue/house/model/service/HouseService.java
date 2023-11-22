package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;

import java.io.IOException;

public interface HouseService {
    HouseDto saveHouse(HouseDto houseDto);
    HouseDealDto saveHouseDeal(HouseDealDto houseDealDto);
    void initData() throws IOException;

}
