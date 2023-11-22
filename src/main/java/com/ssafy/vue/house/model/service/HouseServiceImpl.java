package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.*;
import com.ssafy.vue.house.model.mapper.HouseMapper;
import com.ssafy.vue.station.model.dto.StationCost;
import com.ssafy.vue.station.model.service.StationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class HouseServiceImpl implements HouseService {
    private final HouseMapper mapper;
    private final WebClientService webClientService;
    private final StationServiceImpl stationService;



    public List<HouseDto> findHouseByDong(String start,String end, String money) {
        PriorityQueue<StationCost> priorityQueue = stationService.findByStartAndEnd(start, end);
        List<StationCost> stationCosts = priorityQueue.stream().toList();

        StationCost cost = stationCosts.get(0);
        Set<String> dongSet = cost.getStation().getDong();
        String[] dongList = dongSet.toArray(String[]::new);

        /**
         * 지워야할 부분
         */
        dongList = new String[] {"역삼동","신림동"};

        return mapper.findHouseByDong(dongList);
    }

    @Override
    public List<HouseDealDto> findHouseDealByHouseId(String houseId) {
        return mapper.findHouseDealByHouseId(houseId);
    }


    public HouseDto saveHouse(HouseDto houseDto) {
        try {
            mapper.saveHouse(houseDto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HouseDealDto saveHouseDeal(HouseDealDto houseDealDto) {
        try {
            mapper.saveHouseDeal(houseDealDto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void initData() throws IOException {
        HouseSetupResponse houseSetupResponse = webClientService.getv2();
        for (HouseDto data : houseSetupResponse.getHouseDtoList().values()) {
            try {
                saveHouse(data);
            } catch (Exception e) {
                continue;
            }
        }
        for (HouseDealDto data : houseSetupResponse.getHouseDealDtoList()) {
            try {
                saveHouseDeal(data);
            } catch (Exception e) {
                continue;
            }
        }


//        houseSetupResponse.getHouseDtoList().values().forEach(this::saveHouse);
//        houseSetupResponse.getHouseDealDtoList().forEach(this::saveHouseDeal);
    }
}
