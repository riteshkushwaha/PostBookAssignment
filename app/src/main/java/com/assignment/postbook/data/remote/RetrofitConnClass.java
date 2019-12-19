package com.assignment.postbook.data.remote;


import com.assignment.postbook.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnClass {

    private static Retrofit sRETRO_INSTANCE;

    private static final Object sLock = new Object();

    public static Retrofit getRetrofitInstance() {
        synchronized (sLock) {
            if (sRETRO_INSTANCE == null) {
                sRETRO_INSTANCE = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            }
        }

        return sRETRO_INSTANCE;
    }

    public static APIService getRetrofitAPIService() {
        return getRetrofitInstance().create(APIService.class);
    }
}
