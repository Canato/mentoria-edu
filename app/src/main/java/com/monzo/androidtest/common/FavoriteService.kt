package com.monzo.androidtest.common

import android.content.SharedPreferences

class FavoriteService(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val FAVORITE_ARTICLES_KEY = "FAVORITE_ARTICLES_KEY"
    }

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
    fun toggleFavoriteArticle(articleUrl: String) {
        val favoriteArticles = getAllFavoriteArticles().toMutableSet()

        if (favoriteArticles.contains(articleUrl)) {
            favoriteArticles.remove(articleUrl)
        } else {
            favoriteArticles.add(articleUrl)
        }

        sharedPreferences.edit().putStringSet(FAVORITE_ARTICLES_KEY, favoriteArticles).apply()
    }
}