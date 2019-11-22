package com.share.greencloud.data.api;

import com.share.greencloud.common.Constants;
import com.share.greencloud.domain.interator.BookingRestResponse;
import com.share.greencloud.domain.interator.CallbackListener;
import com.share.greencloud.domain.model.GreenCloudRestResponse;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.domain.model.UserBody;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager apiManager;

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }


    public ApiServices getService() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logger);

        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .header("UserBody-Agent", "android")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("token", Constants.token)
                    //.method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });


        OkHttpClient client = httpClient.build();


        //레트로핏 초기화 BASE URL 설정하는 곳
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //통신인터페이스 기반 서비스 생성
        ApiServices service = retrofit.create(ApiServices.class);
        return service;
    }

    public void join(String planform, String token, CallbackListener<UserBody> callbackListener) {
        callbackListener.startApi();
        getService().join(planform, token).enqueue(new Callback<BookingRestResponse<UserBody>>() {
            @Override
            public void onResponse(Call<BookingRestResponse<UserBody>> call, Response<BookingRestResponse<UserBody>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse() == 0) {
                        callbackListener.callback(response.body().getData());
                    }
                } else {
                    callbackListener.failed(response.message());
                }
                callbackListener.endApi();
            }

            @Override
            public void onFailure(Call<BookingRestResponse<UserBody>> call, Throwable t) {
                callbackListener.endApi();
            }
        });
    }

    public void getUserFavoriteList(Map<String, String> headers, CallbackListener<List<RentalOffice>> callbackListener) {
        callbackListener.startApi();
        getService().getUserFavoritePlace(headers).enqueue(new Callback<GreenCloudRestResponse<RentalOffice>>() {
            @Override
            public void onResponse(Call<GreenCloudRestResponse<RentalOffice>> call, Response<GreenCloudRestResponse<RentalOffice>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult() == 0) {
                        callbackListener.callback(response.body().getModel());
                    }
                } else {
                    callbackListener.failed(response.message());
                }
                callbackListener.endApi();
            }

            @Override
            public void onFailure(Call<GreenCloudRestResponse<RentalOffice>> call, Throwable t) {
                callbackListener.endApi();
            }
        });
    }

    public Single<GreenCloudRestResponse> addUserFavoritePlace(Map<String, String> headers, String office_id) {
        return getService().addUserFavoritePlace(headers,office_id);
    }

    public Single<GreenCloudRestResponse> deleteUserFavoritePlace(Map<String, String> headers, String office_id) {
        return getService().deleteUserFavoritePlace(headers,office_id);
    }
}

