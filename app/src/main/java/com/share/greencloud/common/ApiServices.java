package com.share.greencloud.common;

import com.share.greencloud.model.CurrentWeatherModel;
import com.share.greencloud.model.HourlyWeatherForecastModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://fronteer.kr/service/kmaxy
public interface ApiServices {


    //기상청 일일 예보
    @GET("ForecastSpaceData")
    Call <HourlyWeatherForecastModel> getHourlyWeatherData(@Query(value = "serviceKey", encoded = true) String serviceKey,
                                                           @Query("base_date") String base_date,
                                                           @Query("base_time") String base_time,
                                                           @Query("nx") String nx,
                                                           @Query("ny") String ny,
                                                           @Query("numOfRows") int numOfRows,
                                                           @Query("pageNo") int pageNo,
                                                           @Query("_type") String _type);



    @GET("weather/current/hourly")
    Call<CurrentWeatherModel> getCurrentWeatherData(@Query(value = "appKey") String serviceKey,
                                                    @Query("version") int version,
                                                    @Query("lat") String lat,
                                                    @Query("lon") String lon,
                                                    @Query("_returnType") String _type);

}


