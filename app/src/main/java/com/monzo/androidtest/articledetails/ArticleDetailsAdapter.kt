package com.monzo.androidtest.articles

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
import com.monzo.androidtest.articles.model.Article
import java.util.*

private var context: Context? = null


internal class ArticleDetailsAdapter(
    ctx: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val articles: MutableList<Article> = ArrayList()


    init {
        context = ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.article_details, parent, false)
        return ArticleDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleDetailsViewHolder = holder as ArticleDetailsViewHolder
        articleDetailsViewHolder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun showArticleDetails(article: Article) {
        articles.clear() // Clear existing data
        articles.add(article) // Add the new article
        notifyDataSetChanged()
    }

    class ArticleDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val headlineView = itemView.findViewById<TextView>(R.id.article_details_title_textview)
        private val thumbnailView = itemView.findViewById<ImageView>(R.id.article_details_thumbnail_imageview)
        private val sectionView = itemView.findViewById<TextView>(R.id.article_details_section_textview)
        private val publishedView = itemView.findViewById<TextView>(R.id.article_details_published_textview)
        private val bodyView = itemView.findViewById<TextView>(R.id.article_details_body_textview)

        fun bind(article: Article) {
            headlineView.text = article.title
            sectionView.text = article.sectionId
            publishedView.text = article.published.toString()
            bodyView.text = article.body

            Glide.with(context!!)
                .load(article.thumbnail)
                .into(thumbnailView)
        }
    }
}