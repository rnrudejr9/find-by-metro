package com.ssafy.vue.house.model.mapper;

import com.ssafy.vue.house.model.dto.HouseDealDto;
import com.ssafy.vue.house.model.dto.HouseDto;
import com.ssafy.vue.house.model.dto.HouseRequestDto;
import com.ssafy.vue.house.model.dto.HouseResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Mapper
@Component
public interface HouseMapper {
    void saveHouse(HouseDto houseDto) throws SQLException;
    void saveHouseDeal(HouseDealDto houseDealDto) throws SQLException;

//    HouseResponseDto findHouseDealByHouseAndDong(HouseRequestDto houseRequestDto);
//    @Select("select house_id as houseId, build_year as buildYear, road_name as roadName," +
//            "road_name_bonbun as roadNameBonbun," +
//            "road_name_bubun as roadNameBubun," +
//            "road_name_code as roadNameCode," +
//            "road_name_sigungu_code as roadNameSigunguCode," +
//            "dong," +
//            "bubun," +
//            "apartment_name as apartmentName," +
//            "jibun" +
//            " from house where dong = #{dong}")
    List<HouseDto> findHouseByDong(String[] dongList);
    List<HouseDealDto> findHouseDealByHouseId(String houseId);

    @Select("select house_id as houseId, build_year as buildYear, road_name as roadName," +
            "road_name_bonbun as roadNameBonbun," +
            "road_name_bubun as roadNameBubun," +
            "road_name_code as roadNameCode," +
            "road_name_sigungu_code as roadNameSigunguCode," +
            "dong," +
            "bubun," +
            "apartment_name as apartmentName," +
            "jibun" +
            " from house where house_id = #{houseId}")
    HouseDto findByHouseId(String houseId);
}
