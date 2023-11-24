package com.ssafy.vue.house.model.service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Log4j2
class WebClientServiceImplTest {
    @InjectMocks
    WebClientServiceImpl service;
    @Test
    void getMapFromJSONObject() throws IOException {
        service.getv2();
    }

    @Test
    void getUrl() {
    }

    @Test
    void getv2() {
    }
}