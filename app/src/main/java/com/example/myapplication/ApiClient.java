package com.example.myapplication;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

/**
 * Singleton pour créer et gérer l'instance Retrofit
 * Évite de créer plusieurs instances Retrofit dans l'application
 */
public class ApiClient {
    
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;
    private static Retrofit retrofitAuth = null;
    private static ApiService apiServiceAuth = null;
    private static String currentToken = null;
    
    /**
     * Crée et retourne une instance Retrofit configurée
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Logger pour le débogage (peut être désactivé en production)
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            // Client HTTP avec timeout
            OkHttpClient client = new OkHttpClient.Builder()
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

    /**
     * Crée une instance Retrofit avec un token Bearer
     */
    public static Retrofit getRetrofitInstance(String bearerToken) {
        if (bearerToken == null || bearerToken.isEmpty()) {
            return getRetrofitInstance();
        }
        if (retrofitAuth == null || !bearerToken.equals(currentToken)) {
            currentToken = bearerToken;

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> chain.proceed(
                            chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer " + bearerToken)
                                    .build()
                    ))
                    .addInterceptor(logging)
                    .connectTimeout(ApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(ApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(ApiConfig.TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .build();

            retrofitAuth = new Retrofit.Builder()
                    .baseUrl(ApiConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitAuth;
    }
    
    /**
     * Retourne une instance de l'interface API
     */
    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofitInstance().create(ApiService.class);
        }
        return apiService;
    }

    /**
     * Retourne une instance de l'interface API avec Authorization
     */
    public static ApiService getApiService(String bearerToken) {
        if (bearerToken == null || bearerToken.isEmpty()) {
            return getApiService();
        }
        if (apiServiceAuth == null || !bearerToken.equals(currentToken)) {
            apiServiceAuth = getRetrofitInstance(bearerToken).create(ApiService.class);
        }
        return apiServiceAuth;
    }
}

