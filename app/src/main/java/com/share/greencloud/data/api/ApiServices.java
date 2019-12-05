package com.share.greencloud.data.api;

import com.share.greencloud.domain.interator.BookingRestResponse;
import com.share.greencloud.domain.model.CurrentWeatherModel;
import com.share.greencloud.domain.model.GreenCloudRestResponse;
import com.share.greencloud.domain.model.HourlyWeatherForecastModel;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.domain.model.UserBody;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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


    @GET("weather/current/hourly")
    Call<CurrentWeatherModel> getCurrentWeatherData(@Query(value = "appKey") String serviceKey,
                                                    @Query("version") int version,
                                                    @Query("lat") String lat,
                                                    @Query("lon") String lon,
                                                    @Query("_returnType") String _type);


    //회원가입
    @FormUrlEncoded
    @POST("join")
    Call<BookingRestResponse<UserBody>> join(@Field("platform") String platform, @Field("token") String token);

    @GET("findRentalOffice")
    Call<List<RentalOffice>> getRentalOffices();

    @GET("listFavority")
    Call<GreenCloudRestResponse<RentalOffice>> getUserFavoritePlace(@Header("Authorization") Map<String, String> headers);

    @FormUrlEncoded
    @POST("addFavority")
    Single<Response<GreenCloudRestResponse>> addUserFavoritePlace(@Header("Authorization") Map<String, String> headers, @Field("office_id") String office_id);

    @FormUrlEncoded
    @POST("deleteFavority")
    Single<GreenCloudRestResponse> deleteUserFavoritePlace(@Header("Authorization") Map<String, String> headers, @Field("office_id") String office_id);
}


