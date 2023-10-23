package com.monzo.androidtest.common.injection

import android.content.Context
import com.monzo.androidtest.common.injection.interceptors.AuthInterceptor
import com.monzo.androidtest.common.injection.interceptors.ChuckerInterceptorWrapper
import com.monzo.androidtest.common.injection.interceptors.LoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date

class NetworkModule constructor(context: Context) {

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
    }

    private val authInterceptor: AuthInterceptor = AuthInterceptor(context)
    private val loggingInterceptor: LoggingInterceptor = LoggingInterceptor()
    private val chuckerInterceptor: ChuckerInterceptorWrapper = ChuckerInterceptorWrapper(context)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private fun createOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor.interceptor())
            .addInterceptor(loggingInterceptor.interceptor())
            .addInterceptor(chuckerInterceptor.interceptor())
            .build()
}