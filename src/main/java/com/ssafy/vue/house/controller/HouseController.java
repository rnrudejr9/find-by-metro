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


    @GetMapping("/info")
    public ResponseEntity<?> findByHouseId(@RequestParam String houseId){
        return ResponseEntity.ok().body(houseService.findByHouseId(houseId));
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
    public ResponseEntity<?> findHouseByDong(@RequestParam("start") String start,@RequestParam("end") String end, @RequestParam(value = "money" ,required = false) String money
    ,@RequestParam(value = "page", required = false) String page){
        return ResponseEntity.ok().body(houseService.findHouseByDong(start,end,money,page));
    }

    @GetMapping("/deal")
    public ResponseEntity<?> findHouseDealByHouseId(@RequestParam String houseId){
        return ResponseEntity.ok().body(houseService.findHouseDealByHouseId(houseId));
    }



}
