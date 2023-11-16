package com.ssafy.vue.house.model.mapper;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;

@Mapper
public interface HouseMapper {
    void saveHouse(HouseDto houseDto) throws SQLException;
    void saveHouseDeal(HouseDealDto houseDealDto) throws SQLException;
}
