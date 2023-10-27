package com.monzo.androidtest.common.injection.interceptors

import android.content.Context
import com.monzo.androidtest.R
import okhttp3.Interceptor

class AuthInterceptor constructor(
    private val context: Context
) {

    fun interceptor(): Interceptor =
        Interceptor { chain ->
            val original = chain.request()
            val hb = original.headers.newBuilder()
            hb.add(HEADER_API_KEY, context.resources.getString(R.string.guardian_api_key))
            chain.proceed(original.newBuilder().headers(hb.build()).build())
        }

    companion object {
        private const val HEADER_API_KEY = "api-key"
    }
}

