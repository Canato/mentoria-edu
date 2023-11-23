package com.monzo.androidtest

import android.app.Application
import android.content.Context
import com.monzo.androidtest.articledetails.ArticleDetailsModule
import com.monzo.androidtest.articles.ArticlesModule

class HeadlinesApp : Application() {

    private val articlesModule = ArticlesModule()
    private val articleDetailsModule = ArticleDetailsModule()

    companion object {
        fun fromArticlesModule(applicationContext: Context): ArticlesModule =
            (applicationContext as HeadlinesApp).articlesModule

        fun fromArticlesDetailsModule(applicationContext: Context): ArticleDetailsModule =
            (applicationContext as HeadlinesApp).articleDetailsModule
    }
}
