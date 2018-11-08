package com.example.stefano.bookme.di

import com.example.stefano.bookme.BuildConfig
import com.example.stefano.bookme.data.network.ApiService
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
    fun okHttpClient(): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_VALUE, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_VALUE, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_VALUE, TimeUnit.SECONDS)

        // This is here because Huawei, Sony, Lenovo and other less popular manufacturers tend to hide the debug channel in the logcat.
        // This way, we manually move the logging channel to "warn" using Timber library
        if (BuildConfig.DEBUG) {

            val logger: HttpLoggingInterceptor.Logger = HttpLoggingInterceptor.Logger {
                Timber.tag("OkHttp").w(it)
            }

            okHttpBuilder.addInterceptor(HttpLoggingInterceptor(logger)
                                                 .apply { level = HttpLoggingInterceptor.Level.BODY })
        } else {
            okHttpBuilder.addInterceptor(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}
