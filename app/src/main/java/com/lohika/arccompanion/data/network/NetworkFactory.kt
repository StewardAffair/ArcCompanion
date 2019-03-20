package com.lohika.arccompanion.data.network

import com.lohika.arccompanion.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class NetworkFactory{

    private var retrofitBuilder: Retrofit.Builder

    init {
        val baseRetrofitBuilder = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        val level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(level)
        val timeoutSeconds = 20L

        val baseHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)

        val appHttpClient = baseHttpClientBuilder.build()
        retrofitBuilder = baseRetrofitBuilder.client(appHttpClient)
    }

    fun <T> createApi (apiClass: Class<T>, baseUrl: String) : T = retrofitBuilder.baseUrl(baseUrl).build().create(apiClass)
}