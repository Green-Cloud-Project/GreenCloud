package com.share.greencloud.common;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiFactory {


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <T> T createRetrofitApi(final Class<T> clazz, final String endPoint, Converter.Factory convertFactory) {

        //if (Constants.MODE == Constants.MODE.DEBUG) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        //}


        final Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(endPoint)
                        .addConverterFactory(convertFactory)
                        .client(httpClient.build())
                        .build();


        return retrofit.create(clazz);
    }


}


