package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.dto.HouseResponseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface HouseService {
    HouseDto saveHouse(HouseDto houseDto);
    HouseDealDto saveHouseDeal(HouseDealDto houseDealDto);
    void initData() throws IOException;
    public List<HouseDto> findHouseByDong(String start, String end, String money, String page);

    List<HouseDealDto> findHouseDealByHouseId(String houseId);

    HouseDto findByHouseId(String houseId);
}
