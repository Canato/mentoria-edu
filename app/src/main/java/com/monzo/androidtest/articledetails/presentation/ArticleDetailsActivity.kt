package com.monzo.androidtest.articledetails.presentation

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.*
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R

class ArticleDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleDetailsViewModel
    private lateinit var thumbnailImageView: ImageView
    private lateinit var headlineTextView: TextView
    private lateinit var bodyTextView: TextView

    companion object {
        const val EXTRA_ARTICLE_URL_KEY = "EXTRA_ARTICLE_URL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        thumbnailImageView = findViewById(R.id.thumbnail_imageView)
        headlineTextView = findViewById(R.id.headline_textView)
        bodyTextView = findViewById(R.id.body_textView)

        val articleUrl = intent.getStringExtra(EXTRA_ARTICLE_URL_KEY)
        viewModel = HeadlinesApp.fromArticlesDetailsModule(applicationContext).inject(this, articleUrl!!)

        viewModel.state.observe(this) { state ->
            state?.let { updateUI(state) }
        }
    }

    private fun updateUI(state: ArticleDetailsState) {
        val article = state.article
        headlineTextView.text = article?.title
        bodyTextView.text = article?.body?.let { Html.fromHtml(it) }

        article.let {
            with(this).load(it?.thumbnail).into(thumbnailImageView)
        }
    }
}
