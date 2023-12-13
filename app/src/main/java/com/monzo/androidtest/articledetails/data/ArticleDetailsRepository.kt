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
    }

    fun getArticle(articleUrl: String): Single<ApiArticle> =
        guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
            .map { it.response.content }

    fun getFavoriteState(articleUrl: String): Boolean {
        return sharedPreferences.getBoolean(getFavoriteStateKey(articleUrl), false)
    }

    fun setFavoriteState(articleUrl: String, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(getFavoriteStateKey(articleUrl), isFavorite).apply()
    }

    private fun getFavoriteStateKey(articleUrl: String): String {
        return "$KEY_FAVORITE_STATE_PREFIX$articleUrl"
    }
}