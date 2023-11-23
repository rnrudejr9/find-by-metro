package com.ssafy.vue.station.controller;

import com.ssafy.vue.station.model.dto.StationCost;
import com.ssafy.vue.station.model.service.StationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.PriorityQueue;

@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    private final StationServiceImpl stationServiceImpl;

    @GetMapping
    public void getStation(){
        try {
            stationServiceImpl.init();
            stationServiceImpl.initData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getStationName(@RequestParam("stationName") String stationName){
        return ResponseEntity.ok().body(stationServiceImpl.findByStationName(stationName));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getStationName(){
        return ResponseEntity.ok().body(stationServiceImpl.getStationList().keySet().toArray(String[]::new));
    }

    @GetMapping("/calc")
    public void calcStation(@RequestParam("start") String startStation, @RequestParam("end") String endStation){
        PriorityQueue<StationCost> priorityQueue = stationServiceImpl.findByStartAndEnd(startStation, endStation);
    }
}
