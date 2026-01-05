package com.example.myapplication;

import android.content.Context;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Client Retrofit pour l'Admin API via Bearer token (rôle admin).
 */
public class AdminApiClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context) {
        String token = ClientAuthManager.getToken(context);
        if (token == null) {
            throw new IllegalStateException("Token manquant. Connectez-vous en tant qu'admin.");
        }

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor authInterceptor = chain -> {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + token);
                return chain.proceed(builder.build());
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(logging)
                    .connectTimeout(ApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(ApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(ApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static AdminApiService getService(Context context) {
        return getRetrofitInstance(context).create(AdminApiService.class);
    }
}
