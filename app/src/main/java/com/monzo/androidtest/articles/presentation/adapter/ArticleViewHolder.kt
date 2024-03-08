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
    private val onClick: (articleUrl: String) -> Unit,
    private val onFavoriteClick: (article: Article) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val headlineView = itemView.findViewById<TextView>(R.id.article_headline_textview)
    private val thumbnailView = itemView.findViewById<ImageView>(R.id.article_thumbnail_imageview)
    private val listFavButton = itemView.findViewById<ImageView>(R.id.list_favorite_button)

    fun bind(article: Article) {
        headlineView.text = article.title

        Glide.with(itemView.context)
            .load(article.thumbnail)
            .apply(RequestOptions().transform(CircleCrop()))
            .into(thumbnailView)

        val favoriteDrawableRes = if (article.isFavorite) {
            R.drawable.favorite_filled
        } else {
            R.drawable.favorite_outlined
        }
        listFavButton.setImageResource(favoriteDrawableRes)

        itemView.setOnClickListener { onClick(article.url) }

        listFavButton.setOnClickListener { onFavoriteClick(article) }
    }
}


