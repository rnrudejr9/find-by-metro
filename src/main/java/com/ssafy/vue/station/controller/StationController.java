package com.ssafy.vue.station.controller;

import com.ssafy.vue.station.model.dto.Station;
import com.ssafy.vue.station.model.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @GetMapping
    public void getStation(){
        try {
            stationService.init();
            stationService.initData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
