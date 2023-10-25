package com.monzo.androidtest.articles

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articledetails.ArticleDetailsViewModel
import com.monzo.androidtest.articles.model.Article


class ArticleDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleDetailsViewModel
    private lateinit var adapter: ArticleDetailsAdapter
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        article = intent.getParcelableExtra("ARTICLE")!!

        val thumbnailImageView = findViewById<ImageView>(R.id.article_details_thumbnail_imageview)
        val titleTextView = findViewById<TextView>(R.id.article_title_textview)
        val sectionTextView = findViewById<TextView>(R.id.article_section_textview)
        val publishedTextView = findViewById<TextView>(R.id.article_published_textview)
        val bodyTextView = findViewById<TextView>(R.id.article_body_textview)

        // Set data to views
        Glide.with(this)
            .load(article.thumbnail)
            .into(thumbnailImageView)

        titleTextView.text = article.title
        sectionTextView.text = article.sectionName
        publishedTextView.text = article.published.toString()
        bodyTextView.text = article.body
    }
}
