package com.monzo.androidtest.articles

import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : BaseViewModel<ArticlesState>(ArticlesState()) {
    init {
        disposables += repository.latestFintechArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { articles ->
                setState { copy(refreshing = false, articles = articles) }
            }
    }

    fun onRefresh() {
        disposables += repository.latestFintechArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                setState { copy(refreshing = false, articles = articles) }
            }, { error ->
                // Handle error here, and set refreshing to false
                setState { copy(refreshing = false) }
            })
    }
}
data class ArticlesState(
    val refreshing: Boolean = false,
    val articles: List<Article> = emptyList()
)
