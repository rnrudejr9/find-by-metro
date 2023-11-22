package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.dto.HouseRequestDto;
import com.ssafy.vue.house.model.dto.HouseSetupResponse;
import com.ssafy.vue.house.model.mapper.HouseMapper;
import com.ssafy.vue.station.model.dto.StationCost;
import com.ssafy.vue.station.model.service.StationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService{
    private final HouseMapper mapper;
    private final WebClientService webClientService;
    private final StationServiceImpl stationService;

    public void findHouseByStationDong(){
        PriorityQueue<StationCost> stationCostPriorityQueue = stationService.getPriorityQueue();
        while(!stationCostPriorityQueue.isEmpty()){
            StationCost stationCost = stationCostPriorityQueue.poll();
            String dong = stationCost.getStation().getDong();
        }
    }


    public List<HouseDto> findHouseByDong(String dong){
        return mapper.findHouseByDong(dong);
    }
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
