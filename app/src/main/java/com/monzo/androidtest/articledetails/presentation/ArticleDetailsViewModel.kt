package com.monzo.androidtest.articledetails.presentation

import com.monzo.androidtest.articledetails.data.ArticleDetailsRepository
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleDetailsViewModel(
    val repository: ArticleDetailsRepository,
    val articleUrl: String
) : BaseViewModel<ArticleDetailsState>(
    ArticleDetailsState(null)
) {
    init {
        disposables += repository.getArticle(articleUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { article ->
                val articleDetails = ArticleDetailsInfo(
                    thumbnail = article.fields?.thumbnail!!,
                    title = article.fields.headline!!,
                    body = article.fields.body!!,
                )
                setState { copy(article = articleDetails) }
            }
    }
    fun toggleFavorite() {
        val currentState = state.value
        val newFavoriteState = !currentState!!.isFavorite
        repository.setFavoriteState(articleUrl, newFavoriteState)
        setState { copy(isFavorite = newFavoriteState)}
    }
}

data class ArticleDetailsState(
    val article: ArticleDetailsInfo?,
    val isFavorite: Boolean = false
)
data class ArticleDetailsInfo(
    val thumbnail: String,
    val title: String,
    val body: String,
)