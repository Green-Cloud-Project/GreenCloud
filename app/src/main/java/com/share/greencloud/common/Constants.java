package com.share.greencloud.common;

import android.location.Location;

public class Constants {

//    public static final String API_URL = "http://sarang628.iptime.org:9500/";
    public static final String API_URL = "http://greencloud.iptime.org:1218/";
    public static String token = "";
    public static String userID = "";
    public static String userProfileImage = "";

    public enum MODE {
        DEBUG,
        RELEASE;
    }

    public static MODE MODE = Constants.MODE.DEBUG;

    public static String WEATHER_RESULT_OK = "0000";  //기상청 API, 성공:0000

    //기상청 open api
    public static final String BASE_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/";
    public static final String SERVICE_KEY = "JQO2Q5Yz5pxQLmCZDh%2BwxLgBjgBjfjUxmKiNjSIH4tRibaw%2BdhqWoVp7qYsXhtHSfpycHbIrdTbbCI%2BPz%2B90hA%3D%3D";

    //SK Planet 현재 날씨
    public static final String CURRENT_WEATHER_BASE_URL = "https://apis.openapi.sk.com/";
    public static final String CURRENT_WEATHER_API_KEY = "905ce29f-9b43-46b0-a9e6-3be0cbbc0252";

    public static final String SEOUL_NAME = "Seoul";
    public static final double LATITUE_SEOUL = 37.56;
    public static final double LONGITUTE_SEOUL = 126.97;
    public static final long REQEUST_TIME_INTERVAL = 60000 * 3;
    public static final int CIRCLE_OPTION_COLOR = 0x61b2dfdb;

    public static enum GeoData {
        GANGNAM("강남 대여소", 37.4987548, 127.027777), SEOCHO("서초 대여소", 37.4910180, 127.006799),
        SEONLEOUNG("선릉 대여소", 37.5052801, 127.049777), SAMSEUNG("삼성 대여소", 37.5100758, 127.063861),
        JONGLO("종로 대여소", 37.5697375, 126.988660);

        private String name;
        private double let, lon;

        GeoData(String name, double let, double lon) {
            this.name = name;
            this.let = let;
            this.lon = lon;
        }

        public String getName() {
            return name;
        }

        public double getLet() {
            return let;
        }

        public double getLon() {
            return lon;
        }
    }

    public static Location setDefaultLocation() {
        Location defaultLocation = new Location(SEOUL_NAME);
        defaultLocation.setLatitude(LATITUE_SEOUL);
        defaultLocation.setLongitude(LONGITUTE_SEOUL);
        return defaultLocation;
    }

    public static int fixDistanceError(double originalDistance) {
        // 직선거리로 계산되어 오차가 존재하여
        // 1km 이하일때 + 200m / 1km 이상 + 2km 더해줘야 실제 거리와 동일하게 됨
        if (originalDistance > 1000)
            return (int) originalDistance + 2000;

        else
            return (int) originalDistance + 200;
    }
}
