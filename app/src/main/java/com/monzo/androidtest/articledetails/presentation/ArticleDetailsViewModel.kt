package com.monzo.androidtest.articledetails.presentation

import com.monzo.androidtest.articledetails.data.ArticleDetailsRepository
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleDetailsViewModel(
    private val repository: ArticleDetailsRepository
) : BaseViewModel<ArticleDetailsState>(
    ArticleDetailsState(
        thumbnail = "",
        title = "",
        body = "",
    )
) {

    fun fetchArticle(articleUrl: String) {
        disposables += repository.getArticle(articleUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { article ->
                ArticleDetailsState(
                    thumbnail = article.fields?.thumbnail!!,
                    title = article.fields.headline!!,
                    body = article.fields.body!!,
                )
            }
    }
}

data class ArticleDetailsState(
    val thumbnail: String,
    val title: String,
    val body: String,
)
