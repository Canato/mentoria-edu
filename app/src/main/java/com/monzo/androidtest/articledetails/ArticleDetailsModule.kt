package com.monzo.androidtest.articledetails

import android.content.Context
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.articledetails.data.ArticleDetailsRepository
import com.monzo.androidtest.articledetails.presentation.ArticleDetailsViewModel
import com.monzo.androidtest.articles.data.ArticleMapper
import com.monzo.androidtest.common.injection.NetworkModule

class ArticleDetailsModule {

    fun inject(context: Context, url:String): ArticleDetailsViewModel {
        val networkModule = NetworkModule(context = context)
        val guardianService = networkModule.provideRetrofit().create(GuardianService::class.java)

        return ArticleDetailsViewModel(
            repository = ArticleDetailsRepository(
                guardianService = guardianService,
            ),
            articleUrl = url
        )
    }
}
