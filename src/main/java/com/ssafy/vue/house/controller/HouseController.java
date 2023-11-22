package com.ssafy.vue.house.controller;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.service.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/house")
@Log4j2
public class HouseController {
    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<?> saveHouse(@RequestBody HouseDto houseDto){
        log.debug(houseDto);
        return ResponseEntity.ok().body(houseService.saveHouse(houseDto));
    }

    @PostMapping("/deal")
    public ResponseEntity<?> saveHouseDeal(@RequestBody HouseDealDto houseDealDto){
        log.debug(houseDealDto);
        return ResponseEntity.ok().body(houseService.saveHouseDeal(houseDealDto));
    }

    @GetMapping("/init")
    public ResponseEntity<?> initData() throws IOException {
        log.debug("init");
        houseService.initData();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping()
    public ResponseEntity<?> findHouseByDong(@RequestParam("start") String start,@RequestParam("end") String end, @RequestParam("money") String money){
        return ResponseEntity.ok().body(houseService.findHouseByDong(start,end,money));
    }

    @GetMapping("/deal")
    public ResponseEntity<?> findHouseDealByHouseId(@RequestParam String houseId){
        return ResponseEntity.ok().body(houseService.findHouseDealByHouseId(houseId));
    }



}
