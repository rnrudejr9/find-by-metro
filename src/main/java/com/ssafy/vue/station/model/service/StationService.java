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
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class StationService {
    private static Map<String,Station> stationList = new HashMap<>();


    public void initData() {
        try {
            insertLineData("까치산","2","5");
            setConnectStation("까치산","화곡","신정","신정네사거리");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public static void insertLineData(String stationName, String ...lines){
        for(String lineNumber : lines) {
            stationList.get(stationName).getLine().add(lineNumber);
        }
    }
    public static void setConnectStation(String stationName, String ...destinationNames){
        for(String destinationName : destinationNames) {
            stationList.get(stationName).getConnectStation().add(stationList.get(destinationName));
        }
    }



    public void changeLineData(){
//        stationList.get("역이름")
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
                    .line(new HashSet<>())
                    .lng(lng)
                    .lat(lat)
                    .build();
            stationList.put(name,station);
        }
    }
}

