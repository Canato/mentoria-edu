package com.monzo.androidtest.articles

import android.content.Context
import android.content.res.Resources
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.monzo.androidtest.R
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.articles.model.ArticleMapper
import com.monzo.androidtest.common.injection.NetworkModule
import com.monzo.androidtest.common.injection.interceptors.AuthInterceptor
import com.monzo.androidtest.common.injection.interceptors.ChuckerInterceptorWrapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class ArticlesModule {

    fun inject(context: Context): ArticlesViewModel {
        val networkModule = NetworkModule(context = context)
        val guardianService = networkModule.provideRetrofit().create(GuardianService::class.java)

        return ArticlesViewModel(
            repository = ArticlesRepository(
                guardianService = guardianService,
                articleMapper = ArticleMapper()
            )
        )
    }
}

