package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.dto.HouseSetupResponse;
import com.ssafy.vue.house.model.mapper.HouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService{
    private final HouseMapper mapper;
    private final WebClientService webClientService;

    public HouseDto saveHouse(HouseDto houseDto){
        try {
            mapper.saveHouse(houseDto);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HouseDealDto saveHouseDeal(HouseDealDto houseDealDto) {
        try{
            mapper.saveHouseDeal(houseDealDto);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void initData() throws IOException {
        HouseSetupResponse houseSetupResponse = webClientService.getv2();
        houseSetupResponse.getHouseDtoList().values().forEach(this::saveHouse);
        houseSetupResponse.getHouseDealDtoList().forEach(this::saveHouseDeal);
    }
}
