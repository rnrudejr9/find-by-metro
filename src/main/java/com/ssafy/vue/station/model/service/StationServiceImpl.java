package com.ssafy.vue.station.model.service;

import com.ssafy.vue.station.model.dto.Station;
import com.ssafy.vue.station.model.dto.StationCost;
import com.ssafy.vue.station.model.dto.StationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@RequiredArgsConstructor
@Log4j2
@Service
public class StationServiceImpl {
    private static final Map<String, Station> stationList = new HashMap<>();

    private static final Map<String, Integer> startStationValueMap = new HashMap<>();
    private static final Map<String, Integer> endStationValueMap = new HashMap<>();
    private static final PriorityQueue<StationCost> priorityQueue = new PriorityQueue<>((s1, s2) -> Integer.compare(s1.getValue(), s2.getValue()));

    public Map<String, Station> getStationList() {
        return stationList;
    }

    public PriorityQueue<StationCost> getPriorityQueue() {
        return priorityQueue;
    }

    public PriorityQueue<StationCost> findByStartAndEnd(String startStationName, String endStationName) {
        Station startStation = stationList.get(startStationName);
        Station endStation = stationList.get(endStationName);

        if (startStation == null || endStation == null) {
            log.debug("null 값이 존재함 에러 발생");
        }

        BFS(startStation, startStationValueMap);
        BFS(endStation, endStationValueMap);
        calcValueMap(startStationValueMap, endStationValueMap, priorityQueue);

        return priorityQueue;
    }

    public PriorityQueue<StationCost> findByDijkstra(String startStationName, String endStationName) {
        Station startStation = stationList.get(startStationName);
        Station endStation = stationList.get(endStationName);

        if (startStation == null || endStation == null) {
            log.debug("null 값이 존재함 에러 발생");
        }

        DIJKSTRA();
        return null;
    }

    private void DIJKSTRA() {

    }


    private void calcValueMap(Map<String, Integer> firstMap, Map<String, Integer> secondMap, PriorityQueue<StationCost> que) {
        for (String stationName : stationList.keySet()) {
            Integer firstValue = firstMap.get(stationName);
            Integer secondValue = secondMap.get(stationName);
            if (firstValue != null && secondValue != null) {
                Integer total = firstValue + secondValue;
                que.offer(StationCost.builder().station(stationList.get(stationName)).value(total).build());
            }
        }
    }

    private void BFS(Station station, Map<String, Integer> stationValueMap) {
        Queue<StationCost> que = new ArrayDeque<>();
        que.offer(StationCost.builder().station(station).value(0).before(null).build());
        while (!que.isEmpty()) {
            StationCost cur = que.poll();
            if (stationValueMap.containsKey(cur.getStation().getName())) {
                continue;
            }
            stationValueMap.put(cur.getStation().getName(), cur.getValue());
            for (Station next : cur.getStation().getConnectStation()) {
                boolean isTransfer = true;
                if (cur.getBefore() != null) {
                    for (String curLine : cur.getBefore().getLine()) {
                        if (next.getLine().contains(curLine)) {
                            isTransfer = false;
                        }
                    }
                }
                if (isTransfer && cur.getBefore() != null) {
                    que.offer(StationCost.builder().station(next).value(cur.getValue() + 3).before(cur.getStation()).build());
                } else {
                    que.offer(StationCost.builder().station(next).value(cur.getValue() + 1).before(cur.getStation()).build());
                }

            }
        }
    }


    public void initData() {
        insertLineData("까치산", 2, 5);
        insertDongData("까치산","무슨동");
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
        insertLineData("구로디지털단지", 2);
        setConnectStation("구로디지털단지", "대림", "신대방");
        insertLineData("신대방", 2);
        setConnectStation("신대방", "구로디지털단지", "신림");
        insertLineData("신림", 2);
        setConnectStation("신림", "신대방", "봉천");
        insertLineData("봉천", 2);
        setConnectStation("봉천", "신림", "서울대입구");
        insertLineData("서울대입구", 2);
        setConnectStation("서울대입구", "봉천", "낙성대");
        insertLineData("낙성대", 2);
        setConnectStation("낙성대", "서울대입구", "사당");
        insertLineData("사당", 2, 4);
        setConnectStation("사당", "낙성대", "방배", "이수", "남태령");
        insertLineData("방배", 2);
        setConnectStation("방배", "사당", "서초");
        insertLineData("서초", 2);
        setConnectStation("서초", "방배", "교대");
        insertLineData("교대", 2, 3);
        setConnectStation("교대", "강남", "서초", "고속터미널", "남부터미널");
        insertLineData("강남", 2);
        setConnectStation("강남", "교대", "역삼");
        insertLineData("역삼", 2);
        setConnectStation("역삼", "강남", "선릉");
        insertLineData("선릉", 2);
        setConnectStation("선릉", "역삼", "삼성");
        insertLineData("삼성", 2);
        setConnectStation("삼성", "선릉", "종합운동장");
        insertLineData("종합운동장", 2);
        setConnectStation("종합운동장", "삼성", "신천");
        insertLineData("신천", 2);
        setConnectStation("신천", "종합운동장", "잠실");
        insertLineData("잠실", 2, 8);
        setConnectStation("잠실", "신천", "잠실나루");
        insertLineData("잠실나루", 2);
        setConnectStation("잠실나루", "잠실", "강변");
        insertLineData("강변", 2);
        setConnectStation("강변", "잠실나루", "구의");
        insertLineData("구의", 2);
        setConnectStation("구의", "강변", "건대입구");
        insertLineData("건대입구", 2, 7);
        setConnectStation("건대입구", "구의", "성수");
        insertLineData("성수", 2);
        setConnectStation("성수", "건대입구", "뚝섬", "용답");
        insertLineData("용답", 2);
        setConnectStation("용답", "성수", "신답");
        insertLineData("신답", 2);
        setConnectStation("신답", "용답", "용두");
        insertLineData("용두", 2);
        setConnectStation("용두", "신답", "신설동");
        insertLineData("신설동", 1, 2);
        setConnectStation("신설동", "용두", "동묘앞", "제기동");
        insertLineData("뚝섬", 2);
        setConnectStation("뚝섬", "성수", "한양대");
        insertLineData("한양대", 2);
        setConnectStation("한양대", "뚝섬", "왕십리");
        insertLineData("왕십리", 2, 5);
        setConnectStation("왕십리", "한양대", "상왕십리", "행당", "마장");
        insertLineData("상왕십리", 2);
        setConnectStation("상왕십리", "왕십리", "신당");
        insertLineData("신당", 2, 6);
        setConnectStation("신당", "상왕십리", "동대문역사문화공원", "동묘앞", "청구");
        insertLineData("동대문역사문화공원", 2, 4, 5);
        setConnectStation("동대문역사문화공원", "신당", "을지로4가", "청구", "동대문", "충무로");
        insertLineData("을지로4가", 2, 5);
        setConnectStation("을지로4가", "동대문역사문화공원", "을지로3가", "종로3가");
        insertLineData("을지로3가", 2, 3);
        setConnectStation("을지로3가", "을지로4가", "을지로입구", "종로3가", "충무로");
        insertLineData("을지로입구", 2);
        setConnectStation("을지로입구", "을지로3가", "시청");
        insertLineData("시청", 1, 2);
        setConnectStation("시청", "을지로입구", "충정로", "종각", "서울");
        insertLineData("충정로", 2, 5);
        setConnectStation("충정로", "시청", "아현", "애오개", "서대문");
        insertLineData("아현", 2);
        setConnectStation("아현", "충정로", "이대");
        insertLineData("이대", 2);
        setConnectStation("이대", "아현", "신촌");
        insertLineData("신촌", 2);
        setConnectStation("신촌", "이대", "홍대입구");
        insertLineData("홍대입구", 2);
        setConnectStation("홍대입구", "신촌", "합정");
        insertLineData("합정", 2, 6);
        setConnectStation("합정", "홍대입구", "당산", "상수", "망원");
        insertLineData("당산", 2, 9);
        setConnectStation("당산", "합정", "영등포구청", "선유도", "국회의사당");
        insertLineData("영등포구청", 2, 5);
        setConnectStation("영등포구청", "당산", "문래", "영등포시장", "양평");
        insertLineData("문래", 2);
        setConnectStation("문래", "영등포구청", "신도림");


        insertLineData("까치산", 2, 5);
        setConnectStation("까치산", "화곡", "신정", "신정네거리");
        insertLineData("인천", 1);
        setConnectStation("인천", "동인천");
        insertLineData("동인천", 1);
        setConnectStation("동인천", "인천", "도원");
        insertLineData("도원", 1);
        setConnectStation("도원", "동인천", "제물포");
        insertLineData("제물포", 1);
        setConnectStation("제물포", "도원", "도화");
        insertLineData("도화", 1);
        setConnectStation("도화", "제물포", "주안");
        insertLineData("주안", 1);
        setConnectStation("주안", "도화", "간석");
        insertLineData("간석", 1);
        setConnectStation("간석", "주안", "동암");
        insertLineData("동암", 1);
        setConnectStation("동암", "간석", "백운");
        insertLineData("백운", 1);
        setConnectStation("백운", "동암", "부평");
        insertLineData("부평", 1);
        setConnectStation("부평", "백운", "부개");
        insertLineData("부개", 1);
        setConnectStation("부개", "부평", "송내");
        insertLineData("송내", 1);
        setConnectStation("송내", "부개", "중동");
        insertLineData("중동", 1);
        setConnectStation("중동", "송내", "부천");
        insertLineData("부천", 1);
        setConnectStation("부천", "중동", "소사");
        insertLineData("소사", 1);
        setConnectStation("소사", "부천", "역곡");
        insertLineData("역곡", 1);
        setConnectStation("역곡", "소사", "온수");
        insertLineData("온수", 1, 7);
        setConnectStation("온수", "역곡", "오류동", "천왕", "까치울");
        insertLineData("오류동", 1);
        setConnectStation("오류동", "온수", "개봉");
        insertLineData("개봉", 1);
        setConnectStation("개봉", "오류동", "구일");
        insertLineData("구일", 1);
        setConnectStation("구일", "개봉", "구로");
        insertLineData("구로", 1);
        setConnectStation("구로", "구일", "가산디지털단지", "신도림");
        insertLineData("신도림", 1, 2);
        setConnectStation("신도림", "구로", "도림천", "문래", "영등포", "대림");
        insertLineData("영등포", 1);
        setConnectStation("영등포", "신도림", "신길");
        insertLineData("대방", 1);
        setConnectStation("대방", "신길", "노량진");
        insertLineData("노량진", 1);
        setConnectStation("노량진", "대방", "용산");
        insertLineData("용산", 1);
        setConnectStation("용산", "노량진", "남영");
        insertLineData("남영", 1);
        setConnectStation("남영", "용산", "서울");
        insertLineData("서울", 1, 4);
        setConnectStation("서울", "남영", "시청", "회현", "숙대입구");
        insertLineData("시청", 1);
        setConnectStation("시청", "서울", "종각");
        insertLineData("종각", 1);
        setConnectStation("종각", "시청", "종로3가");
        insertLineData("종로5가", 1);
        setConnectStation("종로5가", "종로3가", "동대문");
        insertLineData("동대문", 1, 4);
        setConnectStation("동대문", "종로5가", "동묘앞", "혜화", "동대문역사문화공원");
        insertLineData("동묘앞", 1);
        setConnectStation("동묘앞", "동대문", "신설동");
        insertLineData("신설동", 1);
        setConnectStation("신설동", "동묘앞", "제기동");
        insertLineData("제기동", 1);
        setConnectStation("제기동", "신설동", "청량리");
        insertLineData("청량리", 1);
        setConnectStation("청량리", "제기동", "회기");
        insertLineData("회기", 1);
        setConnectStation("회기", "청량리", "외대앞");
        insertLineData("외대앞", 1);
        setConnectStation("외대앞", "회기", "신이문");
        insertLineData("신이문", 1);
        setConnectStation("신이문", "외대앞", "석계");
        insertLineData("석계", 1, 6);
        setConnectStation("석계", "신이문", "광운대", "돌곶이", "태릉입구");
        insertLineData("광운대", 1);
        setConnectStation("광운대", "석계", "월계");
        insertLineData("월계", 1);
        setConnectStation("월계", "광운대", "녹천");
        insertLineData("녹천", 1);
        setConnectStation("녹천", "월계", "창동");
        insertLineData("창동", 1, 4);
        setConnectStation("창동", "녹천", "방학", "쌍문", "노원");
        insertLineData("방학", 1);
        setConnectStation("방학", "창동", "도봉");
        insertLineData("도봉", 1);
        setConnectStation("도봉", "방학", "도봉산");
        insertLineData("도봉산", 1, 7);
        setConnectStation("도봉산", "도봉", "망월사");
        insertLineData("망월사", 1);
        setConnectStation("망월사", "도봉산", "회룡");
        insertLineData("회룡", 1);
        setConnectStation("회룡", "망월사", "의정부");
        insertLineData("의정부", 1);
        setConnectStation("의정부", "회룡");
        insertLineData("가산디지털단지", 1);
        setConnectStation("가산디지털단지", "구로", "독산", "철산", "남구로");
        insertLineData("독산", 1);
        setConnectStation("독산", "가산디지털단지", "금천구청");
        insertLineData("금천구청", 1);
        setConnectStation("금천구청", "독산", "광명", "석수");
        insertLineData("광명", 1);
        setConnectStation("광명", "금천구청");
        insertLineData("석수", 1);
        setConnectStation("석수", "금천구청", "관악");
        insertLineData("관악", 1);
        setConnectStation("관악", "석수", "안양");
        insertLineData("안양", 1);
        setConnectStation("안양", "관악", "명학");
        insertLineData("명학", 1);
        setConnectStation("명학", "안양", "금정");
        insertLineData("금정", 1, 4);
        setConnectStation("금정", "명학", "군포");
        insertLineData("군포", 1);
        setConnectStation("군포", "금정", "당정");
        insertLineData("당정", 1);
        setConnectStation("당정", "군포", "의왕");
        insertLineData("의왕", 1);
        setConnectStation("의왕", "당정", "성균관대");
        insertLineData("성균관대", 1);
        setConnectStation("성균관대", "의왕", "화서");
        insertLineData("화서", 1);
        setConnectStation("화서", "성균관대", "수원");
        insertLineData("수원", 1);
        setConnectStation("수원", "화서", "세류");
        insertLineData("세류", 1);
        setConnectStation("세류", "수원", "병점");
        insertLineData("병점", 1);
        setConnectStation("병점", "세류", "서동탄", "세마");
        insertLineData("서동탄", 1);
        setConnectStation("서동탄", "병점");
        insertLineData("세마", 1);
        setConnectStation("세마", "병점", "오산대");
        insertLineData("오산대", 1);
        setConnectStation("오산대", "세마", "오산");
        insertLineData("오산", 1);
        setConnectStation("오산", "오산대", "진위");
        insertLineData("진위", 1);
        setConnectStation("진위", "오산", "송탄");
        insertLineData("송탄", 1);
        setConnectStation("송탄", "진위", "서정리");
        insertLineData("서정리", 1);
        setConnectStation("서정리", "송탄", "지제");
        insertLineData("지제", 1);
        setConnectStation("지제", "서정리", "평택");
        insertLineData("평택", 1);
        setConnectStation("평택", "지제", "성환");
        insertLineData("성환", 1);
        setConnectStation("성환", "평택", "직산");
        insertLineData("직산", 1);
        setConnectStation("직산", "성환", "두정");
        insertLineData("두정", 1);
        setConnectStation("두정", "직산", "천안");
        insertLineData("천안", 1);
        setConnectStation("천안", "두정", "봉명");
        insertLineData("봉명", 1);
        setConnectStation("봉명", "천안", "쌍용");
        insertLineData("쌍용", 1);
        setConnectStation("쌍용", "봉명", "아산");
        insertLineData("아산", 1);
        setConnectStation("아산", "쌍용", "배방");
        insertLineData("배방", 1);
        setConnectStation("배방", "아산", "온양온천");
        insertLineData("온양온천", 1);
        setConnectStation("온양온천", "배방", "신창");
        insertLineData("신창", 1);
        setConnectStation("신창", "온양온천");
        insertLineData("대화", 3);
        setConnectStation("대화", "주엽");
        insertLineData("주엽", 3);
        setConnectStation("주엽", "대화", "정발산");
        insertLineData("정발산", 3);
        setConnectStation("정발산", "주엽", "마두");
        insertLineData("마두", 3);
        setConnectStation("마두", "정발산", "백석");
        insertLineData("백석", 3);
        setConnectStation("백석", "마두", "대곡");
        insertLineData("대곡", 3);
        setConnectStation("대곡", "백석", "화정");
        insertLineData("화정", 3);
        setConnectStation("화정", "대곡", "원당");
        insertLineData("원당", 3);
        setConnectStation("원당", "화정", "삼송");
        insertLineData("삼송", 3);
        setConnectStation("삼송", "원당", "지축");
        insertLineData("지축", 3);
        setConnectStation("지축", "삼송", "구파발");
        insertLineData("구파발", 3);
        setConnectStation("구파발", "지축", "연신내");
        insertLineData("연신내", 3, 6);
        setConnectStation("연신내", "구파발", "불광", "독바위", "구산");
        insertLineData("불광", 3, 6);
        setConnectStation("불광", "연신내", "녹번");
        insertLineData("녹번", 3);
        setConnectStation("녹번", "불광", "홍제");
        insertLineData("홍제", 3);
        setConnectStation("홍제", "녹번", "무악재");
        insertLineData("무악재", 3);
        setConnectStation("무악재", "홍제", "독립문");
        insertLineData("독립문", 3);
        setConnectStation("독립문", "무악재", "경복궁");
        insertLineData("경복궁", 3);
        setConnectStation("경복궁", "독립문", "안국");
        insertLineData("안국", 3);
        setConnectStation("안국", "경복궁", "종로3가");
        insertLineData("종로3가", 1, 3, 5);
        setConnectStation("종로3가", "안국", "을지로3가", "광화문", "종각", "종로5가", "을지로4가");
        insertLineData("을지로3가", 3);
        setConnectStation("을지로3가", "종로3가", "충무로");
        insertLineData("충무로", 3, 4);
        setConnectStation("충무로", "을지로3가", "명동", "동대입구", "동대문역사문화공원");
        insertLineData("동대입구", 3);
        setConnectStation("동대입구", "충무로", "약수");
        insertLineData("약수", 3, 6);
        setConnectStation("약수", "동대입구", "금호");
        insertLineData("금호", 3);
        setConnectStation("금호", "약수", "옥수");
        insertLineData("옥수", 3);
        setConnectStation("옥수", "금호", "압구정");
        insertLineData("압구정", 3);
        setConnectStation("압구정", "옥수", "신사");
        insertLineData("신사", 3);
        setConnectStation("신사", "압구정", "잠원");
        insertLineData("잠원", 3);
        setConnectStation("잠원", "신사", "고속터미널");
        insertLineData("고속터미널", 3, 7, 9);
        setConnectStation("고속터미널", "잠원", "교대", "내방", "반포", "사평", "신반포");
        insertLineData("교대", 2, 3);
        setConnectStation("교대", "고속터미널", "서초", "남부터미널", "강남");
        insertLineData("남부터미널", 3);
        setConnectStation("남부터미널", "교대", "양재");
        insertLineData("양재", 3);
        setConnectStation("양재", "남부터미널", "매봉");
        insertLineData("매봉", 3);
        setConnectStation("매봉", "양재", "도곡");
        insertLineData("도곡", 3);
        setConnectStation("도곡", "매봉", "대치");
        insertLineData("대치", 3);
        setConnectStation("대치", "도곡", "학여울");
        insertLineData("학여울", 3);
        setConnectStation("학여울", "대치", "대청");
        insertLineData("대청", 3);
        setConnectStation("대청", "학여울", "일원");
        insertLineData("일원", 3);
        setConnectStation("일원", "대청", "수서");
        insertLineData("수서", 3);
        setConnectStation("수서", "일원", "가락시장");
        insertLineData("가락시장", 3, 8);
        setConnectStation("가락시장", "수서", "경찰병원", "송파", "문정");
        insertLineData("경찰병원", 3);
        setConnectStation("경찰병원", "가락시장", "오금");
        insertLineData("오금", 3, 5);
        setConnectStation("오금", "경찰병원", "방이", "개롱");
        insertLineData("방화", 5);
        setConnectStation("방화", "개화산");
        insertLineData("개화산", 5);
        setConnectStation("개화산", "방화", "김포공항");
        insertLineData("김포공항", 5, 9);
        setConnectStation("김포공항", "개화산", "송정", "개화", "공항시장");
        insertLineData("송정", 5);
        setConnectStation("송정", "김포공항", "마곡");
        insertLineData("마곡", 5);
        setConnectStation("마곡", "송정", " 발산");
        insertLineData("발산", 5);
        setConnectStation("발산", "마곡", "우장산");
        insertLineData("우장산", 5);
        setConnectStation("우장산", "발산", "화곡");
        insertLineData("화곡", 5);
        setConnectStation("화곡", "우장산", "까치산");
        insertLineData("까치산", 5);
        setConnectStation("까치산", "화곡", "신정", "신정네거리");
        insertLineData("신정", 5);
        setConnectStation("신정", "까치산", "목동");
        insertLineData("목동", 5);
        setConnectStation("목동", "신정", "오목교");
        insertLineData("오목교", 5);
        setConnectStation("오목교", "목동", "양평");
        insertLineData("양평", 5);
        setConnectStation("양평", "오목교", "영등포구청");
        insertLineData("영등포구청", 2, 5);
        setConnectStation("영등포구청", "양평", "영등포시장", "당산", "문래");
        insertLineData("영등포시장", 5);
        setConnectStation("영등포시장", "영등포구청", "신길");
        insertLineData("신길", 1, 5);
        setConnectStation("신길", "영등포시장", "대방", "영등포", "여의도");
        insertLineData("여의도", 5);
        setConnectStation("여의도", "신길", "여의나루");
        insertLineData("여의나루", 5, 9);
        setConnectStation("여의나루", "여의도", "마포");
        insertLineData("마포", 5);
        setConnectStation("마포", "여의나루", "공덕");
        insertLineData("공덕", 5, 6);
        setConnectStation("공덕", "마포", "애오개");
        insertLineData("애오개", 5);
        setConnectStation("애오개", "공덕", "충정로");
        insertLineData("충정로", 2, 5);
        setConnectStation("충정로", "애오개", "서대문");
        insertLineData("서대문", 5);
        setConnectStation("서대문", "충정로", "광화문");
        insertLineData("광화문", 5);
        setConnectStation("광화문", "서대문", "종로3가");
        insertLineData("을지로4가", 2, 5);
        setConnectStation("을지로4가", "을지로3가", "동대문역사문화공원", "종로3가");
        insertLineData("동대문역사문화공원", 2, 4, 5);
        setConnectStation("동대문역사문화공원", "을지로4가", "청구", "동대문", "충무로", "신당");
        insertLineData("청구", 5, 6);
        setConnectStation("청구", "동대문역사문화공원", "신금호");
        insertLineData("신금호", 5);
        setConnectStation("신금호", "청구", "행당");
        insertLineData("행당", 5);
        setConnectStation("행당", "신금호", "왕십리");
        insertLineData("왕십리", 2, 5);
        setConnectStation("왕십리", "행당", "상왕십리", "한양대", "마장");
        insertLineData("마장", 5);
        setConnectStation("마장", "왕십리", "답십리");
        insertLineData("답십리", 5);
        setConnectStation("답십리", "마장", "장한평");
        insertLineData("장한평", 5);
        setConnectStation("장한평", "답십리", "군자");
        insertLineData("군자", 5, 7);
        setConnectStation("군자", "장한평", "아차산", "어린이대공원", "중곡");
        insertLineData("아차산", 5);
        setConnectStation("아차산", "군자", "광나루");
        insertLineData("광나루", 5);
        setConnectStation("광나루", "아차산", "천호");
        insertLineData("천호", 5, 8);
        setConnectStation("천호", "광나루", "강동");
        insertLineData("강동", 5);
        setConnectStation("강동", "천호", "둔촌동", "길동");
        insertLineData("둔촌동", 5);
        setConnectStation("둔촌동", "강동", "올림픽공원");
        insertLineData("올림픽공원", 5);
        setConnectStation("올림픽공원", "둔촌동", "방이");
        insertLineData("방이", 5);
        setConnectStation("방이", "올림픽공원", "오금");
        insertLineData("개롱", 5);
        setConnectStation("개롱", "오금", "거여");
        insertLineData("거여", 5);
        setConnectStation("거여", "개롱", "마천");
        insertLineData("마천", 5);
        setConnectStation("마천", "거여");
        insertLineData("길동", 5);
        setConnectStation("길동", "강동", "굽은다리");
        insertLineData("굽은다리", 5);
        setConnectStation("굽은다리", "길동", "명일");
        insertLineData("명일", 5);
        setConnectStation("명일", "굽은다리", "고덕");
        insertLineData("고덕", 5);
        setConnectStation("고덕", "명일", "상일동");
        insertLineData("상일동", 5);
        setConnectStation("상일동", "고덕");
        insertLineData("부평구청", 7);
        setConnectStation("부평구청", "굴포천");
        insertLineData("굴포천", 7);
        setConnectStation("굴포천", "부평구청", "삼산체육관");
        insertLineData("삼산체육관", 7);
        setConnectStation("삼산체육관", "굴포천", "상동");
        insertLineData("상동", 7);
        setConnectStation("상동", "삼산체육관", "부천시청");
        insertLineData("부천시청", 7);
        setConnectStation("부천시청", "상동", "신중동");
        insertLineData("신중동", 7);
        setConnectStation("신중동", "부천시청", "춘의");
        insertLineData("춘의", 7);
        setConnectStation("춘의", "신중동", "부천종합운동장");
        insertLineData("부천종합운동장", 7);
        setConnectStation("부천종합운동장", "춘의", "까치울");
        insertLineData("까치울", 7);
        setConnectStation("까치울", "부천종합운동장", "온수");
        insertLineData("천왕", 7);
        setConnectStation("천왕", "온수", "광명사거리");
        insertLineData("광명사거리", 7);
        setConnectStation("광명사거리", "천왕", "철산");
        insertLineData("철산", 7);
        setConnectStation("철산", "광명사거리", "가산디지털단지");
        insertLineData("남구로", 7);
        setConnectStation("남구로", "가산디지털단지", "대림");
        insertLineData("대림", 2, 7);
        setConnectStation("대림", "남구로", "신풍", "신도림", "구로디지털단지");
        insertLineData("신풍", 7);
        setConnectStation("신풍", "대림", "보라매");
        insertLineData("보라매", 7);
        setConnectStation("보라매", "신풍", "신대방삼거리");
        insertLineData("신대방삼거리", 7);
        setConnectStation("신대방삼거리", "보라매", "장승배기");
        insertLineData("장승배기", 7);
        setConnectStation("장승배기", "신대방삼거리", "상도");
        insertLineData("상도", 7);
        setConnectStation("상도", "장승배기", "숭실대입구");
        insertLineData("숭실대입구", 7);
        setConnectStation("숭실대입구", "상도", "남성");
        insertLineData("남성", 7);
        setConnectStation("남성", "숭실대입구", "이수");
        insertLineData("이수", 4, 7);
        setConnectStation("반포", "고속터미널", "논현");
        insertLineData("논현", 7);
        setConnectStation("논현", "반포", "학동");
        insertLineData("학동", 7);
        setConnectStation("학동", "논현", "강남구청");
        insertLineData("강남구청", 7);
        setConnectStation("강남구청", "학동", "청담");
        insertLineData("청담", 7);
        setConnectStation("청담", "강남구청", "뚝섬유원지");
        insertLineData("뚝섬유원지", 7);
        setConnectStation("뚝섬유원지", "청담", "건대입구");
        insertLineData("건대입구", 2, 7);
        setConnectStation("건대입구", "뚝섬유원지", "어린이대공원", "성수", "구의");
        insertLineData("어린이대공원", 7);
        setConnectStation("어린이대공원", "건대입구", "군자");
        insertLineData("중곡", 7);
        setConnectStation("중곡", "군자", "용마산");
        insertLineData("용마산", 7);
        setConnectStation("용마산", "중곡", "사가정");
        insertLineData("사가정", 7);
        setConnectStation("사가정", "용마산", "면목");
        insertLineData("면목", 7);
        setConnectStation("면목", "사가정", "상봉");
        insertLineData("상봉", 7);
        setConnectStation("상봉", "면목", "중화");
        insertLineData("중화", 7);
        setConnectStation("중화", "상봉", "먹골");
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

    public void insertDongData(String stationName, String... dong) {
        try {
            Station station = stationList.get(stationName);
            if (station == null) {
                throw new NullPointerException();
            }
            for (String detailDong : dong) {
                station.getDong().add(detailDong);
            }
        } catch (NullPointerException e) {
            log.debug("동 데이터 삽입 중 문제발생 : " + stationName);
        }
    }


    public static void setConnectStation(String stationName, String... destinationNames) {

        for (String destinationName : destinationNames) {
            try {
                if (stationList.get(destinationName) == null)
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
                    .dong(new HashSet<>())
                    .build();
            stationList.put(name, station);
        }
    }


    public StationResponseDto findByStationName(String stationName) {
        return stationList.get(stationName).toDto();
    }
}


