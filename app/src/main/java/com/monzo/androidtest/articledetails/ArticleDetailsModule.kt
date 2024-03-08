package com.monzo.androidtest.articledetails

import android.content.Context
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.articledetails.data.ArticleDetailsRepository
import com.monzo.androidtest.articledetails.presentation.ArticleDetailsViewModel
import com.monzo.androidtest.common.FavoriteService
import com.monzo.androidtest.common.injection.NetworkModule

class ArticleDetailsModule {

    fun inject(context: Context, url:String): ArticleDetailsViewModel {
        val networkModule = NetworkModule(context = context)
        val guardianService = networkModule.provideRetrofit().create(GuardianService::class.java)

        val sharedPreferences = context.getSharedPreferences(
            ArticleDetailsRepository.SHARED_PREF_NAME_KEY,
            Context.MODE_PRIVATE
        )

        return ArticleDetailsViewModel(
            repository = ArticleDetailsRepository(
                guardianService = guardianService,
                favoriteService = FavoriteService(sharedPreferences)
            ),
            articleUrl = url,
            favoriteService = FavoriteService(sharedPreferences))
    }
}
