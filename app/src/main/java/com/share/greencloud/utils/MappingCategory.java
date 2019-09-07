package com.share.greencloud.utils;


import com.share.greencloud.R;

import java.util.HashMap;
import java.util.Map;

public class MappingCategory {

  public enum CATEGORY {SKY, PTY, POP, T1H};  //하늘상태, PTY 강수형태, POP: 강수확율, T1H: 온도

    //PCLOUDY : Partly cloudy, MCLOUDY:Mostly cloudy
  public enum IMG {SUNNY,PCLOUDY,MCLOUDY,CLOUDY,RAIN,RAINSNOW,SNOW}


    public static int mapImage(IMG value) {

        switch (value) {

            case SUNNY:
                return R.drawable.sunny;
            case PCLOUDY:
                return R.drawable.pcloudy;
            case MCLOUDY:
                return R.drawable.mcloudy;
            case CLOUDY:
                return R.drawable.cloudy;
            case RAIN:
                return R.drawable.rain;
            case RAINSNOW:
                return R.drawable.snowrain;
            case SNOW:
                return R.drawable.snow;
            default:
                return R.drawable.sunny;

        }
    }

    public static Map<String,Integer> mapSKY(int value) {

        String skyState = null;
        int    imgResource = 0;

        Map  hm = new HashMap<String,String>();

        switch(value) {

            case 1:
                skyState =  "맑음";
                imgResource = mapImage(IMG.SUNNY);
                break;
            case 2:
                skyState = "구름조금";
                imgResource = mapImage(IMG.PCLOUDY);
                break;
            case 3:
                skyState = "구름많음";
                imgResource = mapImage(IMG.MCLOUDY);
                break;
            case 4:
                skyState = "흐림";
                imgResource = mapImage(IMG.CLOUDY);
                break;
        }
        hm.put(skyState,imgResource);

        return hm;

    }

    public static Map<String,Integer> mapPTY(int value) {

        String chance = null;
        Integer imgResource = 0;

        Map  hm = new HashMap<String,Integer>();

        switch(value) {

            case 1:
                chance = "비";
                imgResource = mapImage(IMG.RAIN);
                break;
            case 2:
                chance = "비/눈";
                imgResource = mapImage(IMG.RAINSNOW);
                break;
            case 3:
                chance = "눈";
                imgResource = mapImage(IMG.SNOW);
                break;
            default:
                chance = "No";
                imgResource = mapImage(IMG.RAIN);

        }
        hm.put(chance,imgResource);
        return hm;
    }

}
