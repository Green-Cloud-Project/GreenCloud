package com.share.greencloud.api;

import com.share.greencloud.common.ApiServices;
import com.share.greencloud.common.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
                    .header("User-Agent", "android")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("token", "")
                    //.method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });


        OkHttpClient client = httpClient.build();


        //레트로핏 초기화 BASE URL 설정하는 곳
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //통신인터페이스 기반 서비스 생성
        ApiServices service = retrofit.create(ApiServices.class);
        return service;
    }

    public void join(String planform, String token, CallbackListener callbackListener) {
        callbackListener.startApi();
        getService().join(planform, token).enqueue(new Callback<BookingRestResponse<String>>() {
            @Override
            public void onResponse(Call<BookingRestResponse<String>> call, Response<BookingRestResponse<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResult() == 0) {
                        callbackListener.callback(response.body().getModel());
                    }
                }
                else {
                    callbackListener.failed(response.message());
                }
                callbackListener.endApi();
            }

            @Override
            public void onFailure(Call<BookingRestResponse<String>> call, Throwable t) {
                callbackListener.endApi();
            }
        });
    }
}

