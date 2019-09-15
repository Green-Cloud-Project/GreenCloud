package com.share.greencloud.common;

import com.share.greencloud.api.BookingRestResponse;
import com.share.greencloud.model.CurrentWeatherModel;
import com.share.greencloud.model.HourlyWeatherForecastModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

//https://fronteer.kr/service/kmaxy
public interface ApiServices {


    //기상청 일일 예보
    @GET("ForecastSpaceData")
    Call<HourlyWeatherForecastModel> getHourlyWeatherData(@Query(value = "serviceKey", encoded = true) String serviceKey,
                                                          @Query("base_date") String base_date,
                                                          @Query("base_time") String base_time,
                                                          @Query("nx") String nx,
                                                          @Query("ny") String ny,
                                                          @Query("numOfRows") int numOfRows,
                                                          @Query("pageNo") int pageNo,
                                                          @Query("_type") String _type);

    //현재날씨
    @GET("ForecastGrib")
    Call<CurrentWeatherModel> getCurrentWeather(@Query(value = "serviceKey", encoded = true) String serviceKey,
                                                @Query("base_date") String base_date,
                                                @Query("base_time") String base_time,
                                                @Query("nx") String nx,
                                                @Query("ny") String ny,
                                                @Query("numOfRows") int numOfRows,
                                                @Query("pageNo") int pageNo,
                                                @Query("_type") String _type);


    //초단기 예보
    @GET("ForecastTimeData")
    Call<HourlyWeatherForecastModel> getShortForecastWeather(@Query(value = "serviceKey", encoded = true) String serviceKey,
                                                             @Query("base_date") String base_date,
                                                             @Query("base_time") String base_time,
                                                             @Query("nx") String nx,
                                                             @Query("ny") String ny,
                                                             @Query("numOfRows") int numOfRows,
                                                             @Query("pageNo") int pageNo,
                                                             @Query("_type") String _type);


    //회원가입
    @FormUrlEncoded
    @POST("join")
    Call<BookingRestResponse<String>> join(@Field("platform") String platform, @Field("token") String token);
}


