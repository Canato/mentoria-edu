package com.monzo.androidtest.articles.presentation

import com.monzo.androidtest.articles.data.ArticlesRepository
import com.monzo.androidtest.articles.domain.Article
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.FavoriteService
import com.monzo.androidtest.common.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticlesViewModel(
    private val repository: ArticlesRepository,
    private val favoriteService: FavoriteService,
    private val articleUrl: String
) : BaseViewModel<ArticlesViewModel.ArticlesState>(
    ArticlesState()
) {

    init {
        fetchLatestArticles()
    }

    fun fetchLatestArticles() {
        disposables += repository.latestFintechArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { articles ->

                val updatedArticles = articles.map { article ->
                    article.copy(isFavorite = repository.getAllFavoriteArticles().contains(article.url))
                }
                setState { copy(refreshing = false, articles = updatedArticles) }
            }
    }

    fun toggleFavorite(article: Article) {
        if (article.isFavorite) {
            repository.removeFavoriteArticle(article.url)
        } else {
            repository.addFavoriteArticle(article.url)
        }

        val updatedArticles = state.value?.articles?.map { oldArticle ->
            if (oldArticle.url == article.url) {
                oldArticle.copy(isFavorite = !oldArticle.isFavorite)
            } else {
                oldArticle
            }
        }
        setState { copy(articles = updatedArticles.orEmpty()) }
    }

    fun onRefresh() {
        setState { copy(refreshing = true) }
        fetchLatestArticles()
    }

    data class ArticlesState(
        val refreshing: Boolean = false,
        val articles: List<Article> = emptyList()
    )
}