package com.ssafy.vue.house.model.service;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.vue.house.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import net.bytebuddy.matcher.FilterableList;
import org.apache.ibatis.jdbc.Null;
import org.apache.tomcat.util.http.parser.Host;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientServiceImpl implements WebClientService{

    private static final String URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    private static final String GET = "GET";
    private static final String serviceKey = "DcyK94HHgmF%2BqT%2Fy39qEl4gb%2BUm1nYXHlUEuKEwgco1zuET1ugc3Y%2BCapUegKIKNByEV67JLw8Dcx%2B1HhFyyuw%3D%3D";

    private static final int SIZE = 9;
    private static String[] DEAL_YML_ARRAY= {"201601","201602","201603","201604","201605","201606","201607","201608","201609"};
    private static String[] LAWD_CD_AARAY = {"11110","11680","11140","11740","11305"};


    public static Map<String, Object> getMapFromJSONObject(JSONObject obj) {
        if (ObjectUtils.isEmpty(obj)) {
            log.error("BAD REQUEST obj : {}", obj);
            throw new IllegalArgumentException(String.format("BAD REQUEST obj %s", obj));
        }
        try {
            return new ObjectMapper().readValue(obj.toString(), Map.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    
    public URL getUrl(int pageNo,int numOfRows,String LAWD_CD,String DEAL_YMD) throws MalformedURLException {
        StringBuilder url = new StringBuilder();

        url.append(URL);
        url.append("?serviceKey=").append(serviceKey);
        url.append("&pageNo=").append(pageNo);
        url.append("&numOfRows=").append(numOfRows);
        url.append("&LAWD_CD=").append(LAWD_CD);
        url.append("&DEAL_YMD=").append(DEAL_YMD);

        return new URL(url.toString());
    }
    public HouseSetupResponse getv2() throws IOException {

        List<Map<String, Object>> xmlData = new ArrayList<>();
        HashMap<String, HouseDto> houseDtoList = new HashMap<>();
        List<HouseDealDto> houseDealDtoList = new ArrayList<>();

        for (int i = 0; i < LAWD_CD_AARAY.length; i++) {

            for (int j = 0; j < DEAL_YML_ARRAY.length; j++) {
                URL url = getUrl(1, 1000, LAWD_CD_AARAY[i], DEAL_YML_ARRAY[j]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(GET);
                int responseCode = connection.getResponseCode();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String inputLine;

                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                bufferedReader.close();

                JSONObject jsonObject = XML.toJSONObject(stringBuffer.toString());
                JSONArray jsonArray = jsonObject.getJSONObject("response")
                        .getJSONObject("body")
                        .getJSONObject("items")
                        .getJSONArray("item");

                for (Object item : jsonArray) {
                    xmlData.add(getMapFromJSONObject((JSONObject) item));
                }

                for (Map<String, Object> item : xmlData) {

                    try {
                        String houseId = item.get("일련번호").toString();
                        int buildYear = Integer.parseInt(item.get("건축년도").toString());
                        String roadName = item.get("도로명").toString();
                        String roadNameBonbun = item.get("도로명건물본번호코드").toString();
                        String roadNameBubun = item.get("도로명건물부번호코드").toString();
                        String roadNameSigunguCode = item.get("도로명시군구코드").toString();
                        String roadNameCode = item.get("도로명코드").toString();
                        String dong = item.get("법정동").toString();
                        String bonbun = item.get("법정동본번코드").toString();
                        String bubun = item.get("법정동부번코드").toString();
                        String apartmentName = item.get("아파트").toString();
                        String jibun = item.get("지번").toString();

                        houseDtoList.put(houseId, HouseDto.builder()
                                .houseId(houseId)
                                .buildYear(buildYear)
                                .roadName(roadName)
                                .roadNameBonbun(roadNameBonbun)
                                .roadNameBubun(roadNameBubun)
                                .roadNameSigunguCode(roadNameSigunguCode)
                                .roadNameCode(roadNameCode)
                                .dong(dong)
                                .bonbun(bonbun)
                                .bubun(bubun)
                                .apartmentName(apartmentName)
                                .jibun(jibun)
                                .build());
                    } catch (NullPointerException e) {

                    }

                    try {
//                int housedealId = item.get("");
                        String houseId = item.get("일련번호").toString();
                        String dealAmount = item.get("거래금액").toString();
                        double area = Double.parseDouble(item.get("전용면적").toString());
                        String dealYear = item.get("년").toString();
                        String dealMonth = item.get("월").toString();
                        String dealDay = item.get("일").toString();
                        String floor = item.get("층").toString();

                        houseDealDtoList.add(HouseDealDto.builder()
                                .houseId(houseId)
                                .dealAmount(dealAmount)
                                .area(area)
                                .dealDay(dealDay)
                                .dealYear(dealYear)
                                .dealMonth(dealMonth)
                                .floor(floor)
                                .build());
                    } catch (NullPointerException e) {

                    }
                }


            }
        }

        log.debug(houseDealDtoList.size() + " ");
        log.debug(houseDtoList.size() + " ");


        return HouseSetupResponse.builder()
                .houseDtoList(houseDtoList)
                .houseDealDtoList(houseDealDtoList)
                .build();
    }
    public void get() throws IOException {
        String origin = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
        String key = "DcyK94HHgmF+qT/y39qEl4gb+Um1nYXHlUEuKEwgco1zuET1ugc3Y+CapUegKIKNByEV67JLw8Dcx+1HhFyyuw==";
        String key2 = "DcyK94HHgmF%2BqT%2Fy39qEl4gb%2BUm1nYXHlUEuKEwgco1zuET1ugc3Y%2BCapUegKIKNByEV67JLw8Dcx%2B1HhFyyuw%3D%3D";
        String decord = "DcyK94HHgmF+qT/y39qEl4gb+Um1nYXHlUEuKEwgco1zuET1ugc3Y+CapUegKIKNByEV67JLw8Dcx+1HhFyyuw==";

        String ans = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=DcyK94HHgmF%2BqT%2Fy39qEl4gb%2BUm1nYXHlUEuKEwgco1zuET1ugc3Y%2BCapUegKIKNByEV67JLw8Dcx%2B1HhFyyuw%3D%3D&pageNo=1&numOfRows=10&LAWD_CD=11110&DEAL_YMD=201512";

        String pageNo = "1";
        String numOfRows = "10";
        String LAWD_CD = "11110";
        String DEAL_YMD = "201512";

        StringBuilder uri = new StringBuilder();
        uri.append(origin);
        uri.append("?serviceKey="+decord);
        uri.append("&pageNo="+pageNo);
        uri.append("&numOfRows="+numOfRows);
        uri.append("&LAWD_CD="+LAWD_CD);
        uri.append("&DEAL_YMD="+DEAL_YMD);

        RestTemplate restTemplate = new RestTemplate();
        String object = restTemplate.getForObject(uri.toString(), String.class);
        log.debug("object " + object);
        ResponseEntity<String> entity1 = restTemplate.getForEntity(uri.toString(), String.class);
        log.debug("entity " + entity1);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        HttpEntity entity = new HttpEntity(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);

        log.debug(response.toString());
        log.debug(uri.toString());
        log.debug(ans);

        log.debug(ans.equals(uri.toString())+ " ");


//        String response = restTemplate.getForObject(temp,String.class);

//        JSONObject jsonObject = XML.toJSONObject(response);

//        log.debug(jsonObject.toString());
//
//        JSONObject object = jsonObject.getJSONObject("response").getJSONObject("items");
//
//        log.debug(object.toString());


    }
}