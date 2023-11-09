package com.monzo.androidtest.articles.presentation.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.domain.Article

class ArticleViewHolder(
    view: View,
    private val onClick: (articleUrl: String) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val headlineView = itemView.findViewById<TextView>(R.id.article_headline_textview)
    private val thumbnailView = itemView.findViewById<ImageView>(R.id.article_thumbnail_imageview)

    fun bind(article: Article, context: Context) {
        headlineView.text = article.title

        // Load the image as a circular image
        Glide.with(context)
            .load(article.thumbnail)
            .apply(RequestOptions().transform(CircleCrop()))
            .into(thumbnailView)

        itemView.setOnClickListener { onClick(article.url) }
    }
}