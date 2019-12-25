/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.model;



import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CurrentWeatherModel {
    @SerializedName("weather")
    public Weather weather;
    @SerializedName("result")
    public Result result;


    public Weather getWeather() {
        return weather;
    }

    public Result getResult() {
        return result;
    }

    public class Weather {

        @SerializedName("hourly")
        public List<Hourly> hourly = null;

        public List<Hourly> getHourly() {
            return hourly;
        }
    }

    //현재 날씨 상태
    public class Hourly {

        @SerializedName("grid")
        private Grid grid;
        @SerializedName("precipitation")
        public Precipitation precipitation;
        @SerializedName("sky")
        public Sky sky;
        @SerializedName("temperature")
        public Temperature temperature;
        @SerializedName("humidity")
        public String humidity;


        public Grid getGrid() {
            return grid;
        }

        public Precipitation getPrecipitation() {
            return precipitation;
        }

        public Sky getSky() {
            return sky;
        }

        public Temperature getTemperature() {
            return temperature;
        }

        public String getHumidity() {
            return humidity;
        }
    }

    //동네정보(위도,경도,지명)
    public class Grid {

        @SerializedName("latitude")
        private String latitude;
        @SerializedName("longitude")
        private String longitude;
        @SerializedName("city")
        private String city;
        @SerializedName("county")
        private String county;
        @SerializedName("village")
        private String village;

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getCity() {
            return city;
        }

        public String getCounty() {
            return county;
        }

        public String getVillage() {
            return village;
        }
    }

    //강수형태 0 :없음 1:비 2: 비/눈 3: 눈
    public class Precipitation {

        @SerializedName("sinceOntime")
        public String sinceOntime;
        @SerializedName("type")
        public String type;

        public String getSinceOntime() {
            return sinceOntime;
        }

        public String getType() {
            return type;
        }
    }

    //하늘상태
    public class Sky {

        @SerializedName("code")
        public String code;
        @SerializedName("name")
        public String name;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    //온도
    public class Temperature {

        @SerializedName("tc")     String tc;
        @SerializedName("tmax")   String tmax;
        @SerializedName("tmin")   String tmin;

        public String getTc() {
            return tc;
        }

        public String getTmax() {
            return tmax;
        }

        public String getTmin() {
            return tmin;
        }
    }

    //Query 결과 코드(OK or Fail)
    public class Result {

        @SerializedName("message") String message;
        @SerializedName("code")    String code;

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }
}