package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface HouseService {
    HouseDto saveHouse(HouseDto houseDto);
    HouseDealDto saveHouseDeal(HouseDealDto houseDealDto);
    void initData() throws IOException;
    public List<HouseDto> findHouseByDong(String dong);

}
