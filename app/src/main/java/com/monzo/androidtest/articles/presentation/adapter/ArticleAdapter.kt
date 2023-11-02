package com.monzo.androidtest.articles.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.domain.Article
import java.util.*

internal class ArticleAdapter(
    private val context: Context,
    private val clickListener: OnArticleClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnArticleClickListener {
        fun onArticleClick(articleUrl: String)
    }

    private var articles: MutableList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(
            view = view,
            onClick = { clickListener.onArticleClick(it) }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleViewHolder = holder as ArticleViewHolder
        articleViewHolder.bind(articles[position], context)
    }

    override fun getItemCount(): Int = articles.size

    fun showArticles(articles: List<Article>) {
        this.articles = articles.toMutableList()
        notifyDataSetChanged()
    }
}