package com.ssafy.vue.house.controller;

import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.service.HouseServiceImpl;
import com.ssafy.vue.interceptor.JWTInterceptor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
@ExtendWith(MockitoExtension.class)
@ComponentScan("com.ssafy.vue.house.*")
@Log4j2
class HouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JWTInterceptor interceptor;
    @BeforeEach
    void initTest() throws Exception {
        when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
        // other stuff
    }
    @Test
    @DisplayName("/house/역삼동")
    public void getHouse() throws Exception {
        String dong = "역삼동";
        mockMvc.perform(get("/vue/house/" + dong)) // 만들어놓은 HelloController에 GET, POST 등의 메소드와 함께 Mock 이용하여 가상으로 접속
                .andExpect(status().isOk());
    }


}