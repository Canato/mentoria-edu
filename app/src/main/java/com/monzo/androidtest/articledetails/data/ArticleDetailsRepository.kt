package com.monzo.androidtest.articledetails.data

import android.content.SharedPreferences
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.common.FavoriteService
import io.reactivex.Single

class ArticleDetailsRepository(
    private val guardianService: GuardianService,
    private val favoriteService: FavoriteService
) {

    companion object {
        const val SHARED_PREF_NAME_KEY = "SHARED_PREF_NAME_KEY"
    }

    fun getArticle(articleUrl: String): Single<ApiArticle> =
        guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
            .map { it.response.content }


    fun getAllFavoriteArticles(): Set<String> = favoriteService.getAllFavoriteArticles()

    fun addFavoriteArticle(articleUrl: String) {
        favoriteService.addFavoriteArticle(articleUrl)
    }

    fun removeFavoriteArticle(articleUrl: String) {
        favoriteService.removeFavoriteArticle(articleUrl)
    }
}