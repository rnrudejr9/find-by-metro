package com.ssafy.vue.station.controller;

import com.ssafy.vue.station.model.service.StationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/calc")
    public void calcStation(@RequestParam("start") String startStation, @RequestParam("end") String endStation){

    }
}
