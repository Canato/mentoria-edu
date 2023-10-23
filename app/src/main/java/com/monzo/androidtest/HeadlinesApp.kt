package com.monzo.androidtest

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import com.monzo.androidtest.articles.ArticlesModule

class HeadlinesApp : Application() {
    private val articlesModule = ArticlesModule()

    companion object {
        fun from(applicationContext: Context): ArticlesModule {
            return (applicationContext as HeadlinesApp).articlesModule
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize Chucker here
        val chuckerInterceptor = ChuckerInterceptor.Builder(this)
            .collector(com.chuckerteam.chucker.api.ChuckerCollector(this))
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .build()

        // Use this OkHttpClient instance for your network requests.
    }
}
