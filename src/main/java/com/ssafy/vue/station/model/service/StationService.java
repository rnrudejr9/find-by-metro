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
    private static Map<String, Station> stationList = new HashMap<>();

    public void getStationList() {
        for(String name : stationList.keySet()){
            log.debug(name);
        }
    }

    public void initData() {
        insertLineData("까치산", 2, 5);
        setConnectStation("까치산", "화곡", "신정", "신정네거리");
        insertLineData("신정네거리", 2);
        setConnectStation("신정네거리", "까치산", "양천구청");
        insertLineData("양천구청", 2);
        setConnectStation("양천구청", "신정네거리", "도림천");
        insertLineData("도림천", 2);
        setConnectStation("도림천", "신도림", "양천구청");
        insertLineData("신도림", 1, 2);
        setConnectStation("신도림", "도림천", "문래", "구로", "영등포", "대림");
        insertLineData("대림", 2, 7);
        setConnectStation("대림", "신도림", "남구로", "신풍", "구로디지털단지");
        insertLineData("구로디지털단지",2);
        setConnectStation("구로디지털단지","대림","신대방");
        insertLineData("신대방",2);
        setConnectStation("신대방","구로디지털단지","신림");
        insertLineData("신림",2);
        setConnectStation("신림","신대방","봉천");
        insertLineData("봉천",2);
        setConnectStation("봉천","신림","서울대입구");
        insertLineData("서울대입구",2);
        setConnectStation("서울대입구","봉천","낙성대");
        insertLineData("낙성대",2);
        setConnectStation("낙성대","서울대입구","사당");
        insertLineData("사당",2,4);
        setConnectStation("사당","낙성대","방배","이수","남태령");
        insertLineData("방배",2);
        setConnectStation("방배","사당","서초");
        insertLineData("서초",2);
        setConnectStation("서초","방배","교대");
        insertLineData("교대",2,3);
        setConnectStation("교대","강남","서초","고속터미널","남부터미널");
        insertLineData("강남",2);
        setConnectStation("강남","교대","역삼");
        insertLineData("역삼",2);
        setConnectStation("역삼","강남","선릉");
        insertLineData("선릉",2);
        setConnectStation("선릉","역삼","삼성");
        insertLineData("삼성",2);
        setConnectStation("삼성","선릉","종합운동장");
        insertLineData("종합운동장",2);
        setConnectStation("종합운동장","삼성","신천");
        insertLineData("신천",2);
        setConnectStation("신천","종합운동장","잠실");
        insertLineData("잠실",2,8);
        setConnectStation("잠실","신천","잠실나루");
        insertLineData("잠실나루",2);
        setConnectStation("잠실나루","잠실","강변");
        insertLineData("강변",2);
        setConnectStation("강변","잠실나루","구의");
        insertLineData("구의",2);
        setConnectStation("구의","강변","건대입구");
        insertLineData("건대입구",2,7);
        setConnectStation("건대입구","구의","성수");
        insertLineData("성수",2);
        setConnectStation("성수","건대입구","뚝섬","용답");
        insertLineData("용답",2);
        setConnectStation("용답","성수","신답");
        insertLineData("신답",2);
        setConnectStation("신답","용답","용두");
        insertLineData("용두",2);
        setConnectStation("용두","신답","신설동");
        insertLineData("신설동",1,2);
        setConnectStation("신설동","용두","동묘앞","제기동");
        insertLineData("뚝섬",2);
        setConnectStation("뚝섬","성수","한양대");
        insertLineData("한양대",2);
        setConnectStation("한양대","뚝섬","왕십리");
        insertLineData("왕십리",2,5);
        setConnectStation("왕십리","한양대","상왕십리","행당","마장");
        insertLineData("상왕십리",2);
        setConnectStation("상왕십리","왕십리","신당");
        insertLineData("신당",2,6);
        setConnectStation("신당","상왕십리","동대문역사문화공원","동묘앞","청구");
        insertLineData("동대문역사문화공원",2,4,5);
        setConnectStation("동대문역사문화공원","신당","을지로4가","청구","동대문","충무로");
        insertLineData("을지로4가",2,5);
        setConnectStation("을지로4가","동대문역사문화공원","을지로3가","종로3가");
        insertLineData("을지로3가",2,3);
        setConnectStation("을지로3가","을지로4가","을지로입구","종로3가","충무로");
        insertLineData("을지로입구",2);
        setConnectStation("을지로입구","을지로3가","시청");
        insertLineData("시청",1,2);
        setConnectStation("시청","을지로입구","충정로","종각","서울");
        insertLineData("충정로",2,5);
        setConnectStation("충정로","시청","아현","애오개","서대문");
        insertLineData("아현",2);
        setConnectStation("아현","충정로","이대");
        insertLineData("이대",2);
        setConnectStation("이대","아현","신촌");
        insertLineData("신촌",2);
        setConnectStation("신촌","이대","홍대입구");
        insertLineData("홍대입구",2);
        setConnectStation("홍대입구","신촌","합정");
        insertLineData("합정",2,6);
        setConnectStation("합정","홍대입구","당산","상수","망원");
        insertLineData("당산",2,9);
        setConnectStation("당산","합정","영등포구청","선유도","국회의사당");
        insertLineData("영등포구청",2,5);
        setConnectStation("영등포구청","당산","문래","영등포시장","양평");
        insertLineData("문래",2);
        setConnectStation("문래","영등포구청","신도림");
    }

    public static void insertLineData(String stationName, int... lines) {
        try {
            for (int lineNumber : lines) {
                stationList.get(stationName).getLine().add(String.valueOf(lineNumber));
            }
        } catch (NullPointerException e) {
            log.debug("호선 데이터 삽입 중 문제발생 : " + stationName);
        }
    }

    public static void setConnectStation(String stationName, String... destinationNames) {

        for (String destinationName : destinationNames) {
            try {
                if(stationList.get(destinationName) == null)
                    throw new NullPointerException();
                stationList.get(stationName).getConnectStation().add(stationList.get(destinationName));
            } catch (NullPointerException e) {
                log.debug("호선 연관관계 설정 중 문제 발생 : " + stationName + " : " + destinationName);
            }
        }
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
                    .connectStation(new HashSet<>())
                    .build();
            stationList.put(name, station);
        }
    }
}

