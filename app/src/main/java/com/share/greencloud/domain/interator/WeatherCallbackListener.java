/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.interator;


public interface WeatherCallbackListener {
    <Y> void getWeatherData(Y weatherModel, Boolean success, String errorMsg);

}
