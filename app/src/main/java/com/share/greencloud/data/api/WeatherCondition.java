/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.data.api;

import android.location.Location;

import com.share.greencloud.common.Constants;
import com.share.greencloud.domain.interator.WeatherCallbackListener;
import com.share.greencloud.domain.model.CurrentWeatherModel;
import com.share.greencloud.domain.model.HourlyWeatherForecastModel;
import com.share.greencloud.utils.BaseTime;
import com.share.greencloud.utils.GridxyConverter;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherCondition {


    public void getHourlyForecastData(Location location, final WeatherCallbackListener listener) {

        BaseTime bt = new BaseTime();
        String[]  gridXy = GridxyConverter.calculateGridxy(location.getLatitude(), location.getLongitude());
        String lat  = gridXy[0];
        String lon  = gridXy[1];

        ApiServices apiServices = ApiFactory.createRetrofitApi(ApiServices.class, Constants.BASE_URL, GsonConverterFactory.create());
        Call<HourlyWeatherForecastModel> call = apiServices.getHourlyWeatherData(Constants.SERVICE_KEY,bt.getToday(),bt.getBaseTime(),lat,lon,50,
                1,"json");

        //if (Constants.MODE == Constants.MODE.DEBUG)  {
            HttpUrl httpUrl = call.request().url();
        //}

        call.enqueue(new Callback<HourlyWeatherForecastModel>() {
            @Override
            public void onResponse(Call<HourlyWeatherForecastModel> call, Response<HourlyWeatherForecastModel> response) {

                if (response.isSuccessful()) {

                    String resultCode = response.body().getResonse().getHeader().getResultCode();
                    if (resultCode.equals("0000")) {
                        if (listener != null)
                            listener.getWeatherData(response.body().getResonse().getBody(), true, "");
                    } else {
                        //listener.getWeatherData(response.body().getResonse().getBody(), true, "");
                    }
                }else {
                    listener.getWeatherData(null, false, response.message());
                }
            }

            @Override
            public void onFailure(Call<HourlyWeatherForecastModel> call, Throwable t) {
                if (listener != null)
                    listener.getWeatherData(null, false, t.getMessage());
            }
        });
    }


    public void getCurrentWeatherData(Location location, final WeatherCallbackListener listener) {



        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());

        ApiServices apiServices = ApiFactory.createRetrofitApi(ApiServices.class, Constants.CURRENT_WEATHER_BASE_URL, GsonConverterFactory.create());

        Call<CurrentWeatherModel> call = apiServices.getCurrentWeatherData(Constants.CURRENT_WEATHER_API_KEY,1,lat,lon,"json");

        //if (Constants.MODE == Constants.MODE.DEBUG)  {
            HttpUrl httpUrl = call.request().url();
        //}

        call.enqueue(new Callback<CurrentWeatherModel>() {
            @Override
            public void onResponse(Call<CurrentWeatherModel> call, Response<CurrentWeatherModel> response) {

                if (response.isSuccessful()) {
                    String resultCode = response.body().getResult().getCode();
                    String resultMsg = response.body().getResult().getMessage();

                    if (resultCode.equals("9200")) {
                        if (listener != null)
                            listener.getWeatherData(response.body().getWeather(), true, "");
                    } else {
                        listener.getWeatherData(null, false, resultMsg);
                    }
                }else {
                    listener.getWeatherData(null, false, response.message());
                }
            }
            @Override
            public void onFailure(Call<CurrentWeatherModel> call, Throwable t) {
                if (listener != null)
                    listener.getWeatherData(null, false, t.getMessage());
            }
        });

    }

}
