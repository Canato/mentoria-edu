package com.monzo.androidtest.common.injection.interceptors

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager

class ChuckerInterceptorWrapper constructor(
    private val context: Context
) {

    private val chuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    fun interceptor(): ChuckerInterceptor =
        ChuckerInterceptor
            .Builder(context = context)
            .collector(collector = chuckerCollector)
            .maxContentLength(length = 250_000L)
            .alwaysReadResponseBody(enable = true)
            .build()
}