package com.monzo.androidtest.articledetails.data

import android.content.Context
import android.content.SharedPreferences
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import io.reactivex.Single

class ArticleDetailsRepository(
    private val guardianService: GuardianService,
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_NAME_KEY, Context.MODE_PRIVATE)
    }

    companion object {
        private const val SHARED_PREF_NAME_KEY = "SHARED_PREF_NAME_KEY"
        private const val FAVORITE_STATE_PREFIX_KEY = "KEY_FAVORITE_STATE_PREFIX_KEY"
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