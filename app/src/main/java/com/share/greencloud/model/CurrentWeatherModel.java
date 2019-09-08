package com.share.greencloud.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CurrentWeatherModel {

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

        @SerializedName("category")
        @Expose
        private String category;

        @SerializedName("obsrValue")
        @Expose
        private String  obsrValue;

        public String getCategory() {
            return category;
        }

        public String  getObsrValue() {
            return obsrValue;
        }
    }

}