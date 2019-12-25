/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.utils;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class BaseTime {

    private Calendar calendar;
    private int hour;
    private int baseTime;
    private boolean yesterday;


    public BaseTime() {

        calendar = Calendar.getInstance(); // 현재시간 가져옴
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        baseTime = getStartBaseTime(hour);

    }


    public int getStartBaseTime(int t) {

        if (t >= 0) {
            if (t < 2) {
                yesterday = true;
                return 23;  //하루전 23시.
            } else {
                return t - (t + 1) % 3;
            }
        } else
            return -1;
    }


    public String getBaseTime() {
        if (baseTime / 10 > 0)
            return baseTime + "00";
        else
            return "0" + baseTime + "00";
    }


    public String getToday()  {

        String basetDate = null;

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date oneDayBefore = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);

        if (yesterday) {
            basetDate = format.format(oneDayBefore);
        }else {
            basetDate = format.format(date);
        }

        return  basetDate;

    }

    public String minus30Minutes()  {


        int time = Integer.parseInt(getBaseTime());
        int   minus30s =  time - 70;
        return  String.valueOf(minus30s);
    }

    public String addAMPM(String time) {
        return  (Integer.parseInt(time) < 1200 ) ? "AM" : "PM";
    }

}

