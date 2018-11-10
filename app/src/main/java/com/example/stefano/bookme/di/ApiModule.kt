package com.example.stefano.bookme.di

import android.content.Context
import com.example.stefano.bookme.BuildConfig
import com.example.stefano.bookme.data.network.ApiService
import com.example.stefano.bookme.data.network.MoshiProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECT_TIMEOUT_VALUE = 10L
private const val WRITE_TIMEOUT_VALUE = 10L
private const val READ_TIMEOUT_VALUE = 10L

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun moshi(): Moshi = MoshiProvider.moshi

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_VALUE, TimeUnit.SECONDS)


        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Timber.tag("OkHttp").i(it)
            }).apply { level = HttpLoggingInterceptor.Level.BODY })
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_API_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(okHttpClient)
                    .build()
}
