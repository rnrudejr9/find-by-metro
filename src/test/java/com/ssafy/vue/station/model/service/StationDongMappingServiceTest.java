package com.ssafy.vue.station.model.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Log4j2
class StationDongMappingServiceTest {

    @InjectMocks
    StationDongMappingService service;
    @Test
    void crwaling() {
        service.crwaling();
    }
}