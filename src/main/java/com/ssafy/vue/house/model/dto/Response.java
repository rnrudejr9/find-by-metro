package com.ssafy.vue.house.model.dto;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="response")
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
    @XmlElement(name="header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    @Getter
    @Setter
    @XmlRootElement(name = "header")
    private static class Header {

        private String resultCode;
        private String resultMsg;
    }


    @Getter
    @Setter
    @XmlRootElement(name = "body")
    private static class Body {

        private Items items;
        private String numOfRows;
        private String pageNo;
        private String totalCount;

        @Getter
        @Setter
        @XmlRootElement(name = "items")
        private static class Items {

            private List<Item> item;

            @Getter
            @Setter
            @XmlRootElement(name = "item")
            public static class Item {

//                @XmlElement(name="일련번호")
                private String 일련번호;

            }
        }
    }
}
