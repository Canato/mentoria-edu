package com.monzo.androidtest.articledetails.data

import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.api.model.ApiArticleDetailsResponse
import io.reactivex.Single

class ArticleDetailsRepository(
    private val guardianService: GuardianService,
) {
    fun getArticle(articleUrl: String,): Single<ApiArticle> =
        guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
            .map { it.response.content }
}
