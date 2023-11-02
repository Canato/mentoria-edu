package com.monzo.androidtest.articledetails.data

import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.articles.domain.Article
import com.monzo.androidtest.articles.data.ArticleMapper
import io.reactivex.Single

class ArticleDetailsRepository(
    private val guardianService: GuardianService,
) {
    fun getArticle(articleUrl: String): Single<ApiArticle> =
        guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
}
