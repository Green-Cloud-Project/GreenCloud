package com.share.greencloud.common;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiFactory {


    public static <T> T createRetrofitApi(final Class<T> clazz, final String endPoint, Converter.Factory convertFactory) {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(endPoint).addConverterFactory(convertFactory).build();
        return retrofit.create(clazz);
    }


}


