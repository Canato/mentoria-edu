package com.monzo.androidtest.articles.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monzo.androidtest.articles.domain.Article
import com.monzo.androidtest.R

class ArticleAdapter(
    private val context: Context,
    private val clickListener: OnArticleClickListener,
    private val favoriteClickListener: OnFavoriteClickListener
) : RecyclerView.Adapter<ArticleViewHolder>() {

    interface OnArticleClickListener {
        fun onArticleClick(articleUrl: String)
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(article: Article)
    }

    private var articles: MutableList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(
            view = view,
            onClick = { clickListener.onArticleClick(it) },
            onFavoriteClick = { article -> favoriteClickListener.onFavoriteClick(article) }
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    fun showArticles(articles: List<Article>) {
        this.articles = articles.toMutableList()
        notifyDataSetChanged()
    }
}

