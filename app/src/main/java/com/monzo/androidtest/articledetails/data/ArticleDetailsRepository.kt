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
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val SHARED_PREF_NAME = "ArticleDetailsPrefs"
        private const val KEY_FAVORITE_STATE_PREFIX = "favorite_state_"
        private const val KEY_FAVORITE_ARTICLES = "favorite_articles"
    }

    fun getArticle(articleUrl: String): Single<ApiArticle> =
        guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
            .map { it.response.content }


    fun getAllFavoriteArticles(): Set<String> {
        return sharedPreferences.all.keys.filter { it.startsWith(KEY_FAVORITE_STATE_PREFIX) }
            .map { it.removePrefix(KEY_FAVORITE_STATE_PREFIX) }
            .toSet()
    }

    fun addFavoriteArticle(articleUrl: String) {
        val favoriteArticles = getAllFavoriteArticles().toMutableSet()
        favoriteArticles.add(articleUrl)
        sharedPreferences.edit().putStringSet(KEY_FAVORITE_ARTICLES, favoriteArticles).apply()
    }

    fun removeFavoriteArticle(articleUrl: String) {
        val favoriteArticles = getAllFavoriteArticles().toMutableSet()
        favoriteArticles.remove(articleUrl)
        sharedPreferences.edit().putStringSet(KEY_FAVORITE_ARTICLES, favoriteArticles).apply()
    }
}