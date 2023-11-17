package com.ssafy.vue.station.model.service;


import com.ssafy.vue.station.model.dto.Station;
import org.json.JSONException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {
    @InjectMocks
    StationService stationService;

    private final Map<String,Station> stationList = new HashMap<>();

    @Test
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
                    .line(line)
                    .lng(lng)
                    .lat(lat)
                    .build();
            stationList.put(name,station);
        }

        for(Station s : stationList.values()){
            System.out.println(s);
        }



    }
}