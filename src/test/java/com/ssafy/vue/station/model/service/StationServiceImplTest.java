package com.ssafy.vue.station.model.service;



import com.ssafy.vue.station.model.dto.Station;
import com.ssafy.vue.station.model.dto.StationCost;
import lombok.extern.log4j.Log4j2;

import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;

@ExtendWith(MockitoExtension.class)
@Log4j2
class StationServiceImplTest {

    @InjectMocks
    StationServiceImpl stationService;

    @Test
    @BeforeEach
    @DisplayName("초기화 설정 및 연관관계 설정")
    void init() throws IOException, ParseException {
        stationService.init();
        stationService.initData();
    }

    @Test
    @DisplayName("동정보 조회기능 테스트")
    void setDong() {
        String stationName = "까치산";
        String dong = "동 정보";
        String dong2 = "변경된 동 정보";
        Map<String, Station> list = stationService.getStationList();
        Station station = list.get(stationName);
        list.put(stationName,station);
;


        stationService.insertDongData(stationName,dong2);
        Assertions.assertThat(list.get(stationName).getDong()).isEqualTo(dong);
        log.debug(stationService.getStationList().get(stationName).getDong());
    }

    @Test
    @DisplayName("BFS탐색 기능 테스트")
    void BFS(){
        PriorityQueue<StationCost> priorityQueue = stationService.findByStartAndEnd("신림", "노량진");
        while(!priorityQueue.isEmpty()){
            StationCost cost = priorityQueue.poll();
            log.debug(cost.getStation().getName() + " " +cost.getValue());
        }
    }

    @Test
    @After
    @DisplayName("결과값이 잘 반영되어있는지 테스트")
    void finish(){
        Map<String, Station> stationList = stationService.getStationList();
        Assertions.assertThat(10)
                .isEqualTo(stationList.keySet().size());
    }
}