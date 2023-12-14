package com.monzo.androidtest.articledetails.data

import android.content.SharedPreferences
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import io.reactivex.Single

class ArticleDetailsRepository(
    private val guardianService: GuardianService,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val SHARED_PREF_NAME_KEY = "SHARED_PREF_NAME_KEY"
        private const val FAVORITE_ARTICLES_KEY = "FAVORITE_ARTICLES_KEY"
    }

    fun getArticle(articleUrl: String): Single<ApiArticle> =
        guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
            .map { it.response.content }


    fun getAllFavoriteArticles(): Set<String> {
        return sharedPreferences.getStringSet(FAVORITE_ARTICLES_KEY, null) ?: setOf()
    }

    fun addFavoriteArticle(articleUrl: String) {
        val favoriteArticles = getAllFavoriteArticles().toMutableSet()
        favoriteArticles.add(articleUrl)
        sharedPreferences.edit().putStringSet(FAVORITE_ARTICLES_KEY, favoriteArticles).apply()
    }

    fun removeFavoriteArticle(articleUrl: String) {
        val favoriteArticles = getAllFavoriteArticles().toMutableSet()
        favoriteArticles.remove(articleUrl)
        sharedPreferences.edit().putStringSet(FAVORITE_ARTICLES_KEY, favoriteArticles).apply()
    }
}