package com.monzo.androidtest.articles.data

import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.articles.domain.Article
import io.reactivex.Single

class ArticlesRepository(
    private val guardianService: GuardianService,
    private val articleMapper: ArticleMapper
) {
    fun latestFintechArticles(): Single<List<Article>> {
        return guardianService.searchArticles("fintech,brexit")
            .map { articleMapper.map(it) }
    }

    fun getArticle(articleUrl: String): Single<ApiArticle> {
        return guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
    }
}


