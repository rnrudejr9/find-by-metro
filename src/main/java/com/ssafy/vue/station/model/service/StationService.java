package com.ssafy.vue.station.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.station.model.dto.Station;
import com.ssafy.vue.station.model.dto.StationCost;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class StationService {
    private final Map<String,Station> stationList = new HashMap<>();


    public void initData(){
        stationList.get("이촌").getConnectStation().add(stationList.get("잠실"));
        stationList.get("잠실").getConnectStation().add(stationList.get("이촌"));
    }

    public void init() throws IOException, ParseException {
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

    }
}
