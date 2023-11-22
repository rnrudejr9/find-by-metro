package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.mapper.HouseMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log4j2
class HouseServiceImplTest {
    @Autowired
    private HouseMapper mapper;
    @Test
    void findHouseByDong() {
        List<HouseDto> house = mapper.findHouseByDong("신림동");
        for(HouseDto houseDto : house){
            log.debug(houseDto);
        }
        log.debug(house.size());
    }
}