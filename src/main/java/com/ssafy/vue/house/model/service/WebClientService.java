package com.ssafy.vue.house.model.service;

import com.ssafy.vue.house.model.dto.HouseSetupResponse;

import java.io.IOException;
import java.net.MalformedURLException;

public interface WebClientService {
    void get() throws IOException;
    HouseSetupResponse getv2() throws IOException;
}
