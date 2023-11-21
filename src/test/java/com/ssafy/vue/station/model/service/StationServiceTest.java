package com.ssafy.vue.station.model.service;


import com.ssafy.vue.station.model.dto.Station;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Log4j2
class StationServiceTest {
    @InjectMocks
    StationService stationService;

    private static Map<String, Station> stationList = new HashMap<>();

    @After
    @Test
    void finialData() {
        for (Station station : stationList.get("까치산").getConnectStation()) {
            if (station == null)
                continue;
            log.debug(station.getName());
        }
    }

    @Test
    @DisplayName("init Data test")
    void initData() {
//        stationService.getStationList();
//        stationService.init();
//        stationService.initData();


    }

    public static void insertLineData(String stationName, String... lines) {
        try {
            for (String lineNumber : lines) {
                stationList.get(stationName).getLine().add(lineNumber);
            }
        } catch (NullPointerException e) {
            log.debug("호선 데이터 삽입 중 문제발생 : " + stationName);
        }
    }

    public static void setConnectStation(String stationName, String... destinationNames) {
        for (String destinationName : destinationNames) {
            try {
                if (stationList.get(destinationName) == null)
                    throw new NullPointerException();
                stationList.get(stationName).getConnectStation().add(stationList.get(destinationName));
            } catch (NullPointerException e) {
                log.debug("호선 연관관계 설정 중 문제 발생 : " + destinationName + " : " + stationName);
            }
        }

    }

    @Test
    @Before
    @DisplayName("JSON Parsing TEST")
    void init() throws IOException, ParseException, JSONException {


        Object object = new JSONParser().parse(new FileReader("station/station_coordinate.json"));
        org.json.simple.JSONArray array = (JSONArray) object;

        for (int i = 0; i < array.size(); i++) {
            JSONObject json = (JSONObject) array.get(i);
            String name = (String) json.get("name");
            String code = ((Long) json.get("code")) + "";
            String line = (String) json.get("line");
            Double lat = (Double) json.get("lat");
            Double lng = (Double) json.get("lng");

            Station station = Station.builder()
                    .name(name)
                    .code(code)
                    .line(new HashSet<>())
                    .lng(lng)
                    .lat(lat)
                    .connectStation(new HashSet<>())
                    .build();
            stationList.put(name, station);
        }

    }
}