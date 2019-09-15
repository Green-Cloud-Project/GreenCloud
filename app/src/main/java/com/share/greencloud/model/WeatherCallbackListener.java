package com.share.greencloud.model;


public interface WeatherCallbackListener {
    <Y> void getWeatherData(Y weatherModel, Boolean success, String errorMsg);

}
