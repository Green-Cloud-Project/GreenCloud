package com.share.greencloud.common;

public class Constants {

    public enum MODE {
        DEBUG,
        RELEASE;
    }

    public static MODE MODE = Constants.MODE.DEBUG;

    public static String WEATHER_RESULT_OK  = "0000";  //기상청 API, 성공:0000

    //기상청 open api
    public static final String BASE_URL    = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/";
    public static final String SERVICE_KEY = "JQO2Q5Yz5pxQLmCZDh%2BwxLgBjgBjfjUxmKiNjSIH4tRibaw%2BdhqWoVp7qYsXhtHSfpycHbIrdTbbCI%2BPz%2B90hA%3D%3D";

    public static final double LATITUE_SEOUL = 37.56;
    public static final double LONGITUTE_SEOUL = 126.97;

}
