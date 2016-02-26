package com.chefcode.android.patungan.services;

import com.google.gson.Gson;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module
public class PushnotifServiceModule {

    @Pushnotification
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ServiceConfigs.PUSH_NOTIF_SERVICE)
                .client(client)
                .build();
    }

}
