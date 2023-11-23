package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.*;
import com.ssafy.vue.house.model.mapper.HouseMapper;
import com.ssafy.vue.station.model.dto.StationCost;
import com.ssafy.vue.station.model.service.StationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class HouseServiceImpl implements HouseService {
    private final HouseMapper mapper;
    private final WebClientService webClientService;
    private final StationServiceImpl stationService;



    public List<HouseDto> findHouseByDong(String start,String end, String money, String page) {
        PriorityQueue<StationCost> priorityQueue = stationService.findByStartAndEnd(start, end);
        List<StationCost> stationCosts = priorityQueue.stream().toList();

        StationCost cost = stationCosts.get(Integer.parseInt(page));

        Set<String> dongSet = cost.getStation().getDong();
        String[] dongList = dongSet.toArray(String[]::new);

        /**
         * 지워야할 부분
         */
        String[][] dongListArray = new String[5][2];
        dongListArray[0] = new String[] {"역삼동","신림동"};
        dongListArray[1] = new String[] {"삼성동"};
        dongListArray[2] = new String[] {"대치동"};
        dongListArray[3] = new String[] {"수서동"};
        dongListArray[4] = new String[] {"청담동"};

        return mapper.findHouseByDong(dongListArray[Integer.parseInt(page)]);
    }

    @Override
    public List<HouseDealDto> findHouseDealByHouseId(String houseId) {
        return mapper.findHouseDealByHouseId(houseId);
    }

    @Override
    public HouseDto findByHouseId(String houseId) {
        return mapper.findByHouseId(houseId);
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
