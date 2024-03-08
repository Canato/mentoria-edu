package com.monzo.androidtest.articles

import android.content.Context
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.articledetails.data.ArticleDetailsRepository
import com.monzo.androidtest.articles.data.ArticlesRepository
import com.monzo.androidtest.articles.data.ArticleMapper
import com.monzo.androidtest.articles.presentation.ArticlesViewModel
import com.monzo.androidtest.common.FavoriteService
import com.monzo.androidtest.common.injection.NetworkModule

class ArticlesModule {

    fun inject(context: Context, url: String): ArticlesViewModel {
        val networkModule = NetworkModule(context = context)
        val guardianService = networkModule.provideRetrofit().create(GuardianService::class.java)

        val sharedPreferences = context.getSharedPreferences(
            ArticleDetailsRepository.SHARED_PREF_NAME_KEY,
            Context.MODE_PRIVATE
        )

        return ArticlesViewModel(
            repository = ArticlesRepository(
                guardianService = guardianService,
                articleMapper = ArticleMapper(),
                favoriteService = FavoriteService(sharedPreferences)
            ),
            articleUrl = url,
            favoriteService = FavoriteService(sharedPreferences))
    }
}

