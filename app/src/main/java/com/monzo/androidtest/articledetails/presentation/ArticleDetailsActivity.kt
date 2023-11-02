package com.monzo.androidtest.articledetails.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R

class ArticleDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleDetailsViewModel
    private lateinit var adapter: ArticleDetailsAdapter

    companion object {
        const val EXTRA_ARTICLE_URL_KEY = "EXTRA_ARTICLE_URL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.article_details_recyclerview)

        setSupportActionBar(toolbar)

        viewModel = HeadlinesApp.fromArticlesDetailsModule(applicationContext).inject(this)
        val articleUrl = intent.getStringExtra(EXTRA_ARTICLE_URL_KEY)

        viewModel.fetchArticle(articleUrl!!)

        adapter = ArticleDetailsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.state.observe(this) { state ->
            adapter.showArticleDetails(state)
        }
    }
}
