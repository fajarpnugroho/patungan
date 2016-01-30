package com.chefcode.android.patungan.services;

import com.chefcode.android.patungan.services.api.LoginService;
import com.chefcode.android.patungan.services.api.LogoutService;
import com.chefcode.android.patungan.services.api.UserService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module
public class ServiceModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());
        return client;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ServiceConfigs.SERVICE_BASE_URL)
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }

    @Singleton
    @Provides
    LogoutService provideLogoutService(Retrofit retrofit) {
        return retrofit.create(LogoutService.class);
    }

    @Singleton
    @Provides
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

}
