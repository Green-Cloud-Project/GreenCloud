package com.share.greencloud.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class WeatherForecastModel {

    @SerializedName("response")
    @Expose
    public Response response;

    public Response getResonse() {
        return response;
    }


    public class Response  {

        @SerializedName("header")
        @Expose
        public Header header;

        public Header getHeader() {
            return header;
        }

        @SerializedName("body")
        @Expose
        public Body body;

        public Body getBody() {
            return body;
        }
    }

    public class Header {

        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        public String getResultCode() {
            return resultCode;
        }
    }

    public class Body {

        @SerializedName("items")
        //@Expose
        //private Item  item;
        @Expose
        private Items items = null;

        public Items getItem() {
            return items;
        }
    }

    public class Items {

        @SerializedName("item")
        @Expose
        private List<Item> items = null;

        public List<Item> getItems() {
            return items;
        }
    }

    public class Item {

        @SerializedName("baseDate")
        @Expose
        private String  baseDate;

        @SerializedName("baseTime")
        @Expose
        private String baseTime;

        @SerializedName("category")
        @Expose
        private String category;

        @SerializedName("fcstDate")
        @Expose
        private String fcstDate;

        @SerializedName("fcstTime")
        @Expose
        private String fcstTime;

        @SerializedName("fcstValue")
        @Expose
        private String  fcstValue;

        public String getBaseDate() {
            return baseDate;
        }

        public String getBaseTime() {
            return baseTime;
        }

        public String getCategory() {
            return category;
        }

        public String getFcstDate() {
            return fcstDate;
        }

        public String getFcstTime() {
            return fcstTime;
        }

        public String  getFcstValue() {
            return fcstValue;
        }
    }

}