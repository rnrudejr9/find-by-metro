package com.ssafy.vue.station.model.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


@Service
public class StationDongMappingService {
    public void crwaling(){
        String url = "https://map.naver.com/p/search/봉은사역";

        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            System.out.println(e);
        }

        // 1. 주요 뉴스로 나오는 태그 찾아서 가져옴.
        Elements element = doc.select("#_pcmap_list_scroll_container > ul > li:nth-child(1)");
        Elements div = element.select("div");
    }
}
