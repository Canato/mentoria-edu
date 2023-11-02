package com.monzo.androidtest.articledetails.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.presentation.ArticlesViewModel
import com.monzo.androidtest.articles.domain.Article



class ArticleDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleDetailsViewModel
    private lateinit var adapter: ArticleDetailsAdapter
    private lateinit var article: Article

    companion object {
        const val EXTRA_ARTICLE_URL_KEY = "EXTRA_ARTICLE_URL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.article_details_swiperefreshlayout)
        val recyclerView = findViewById<RecyclerView>(R.id.article_details_recyclerview)

        setSupportActionBar(toolbar)

        viewModel = HeadlinesApp.fromArticlesDetailsModule(applicationContext).inject(this)

        adapter = ArticleDetailsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(this) { state ->
            swipeRefreshLayout.isRefreshing = state.refreshing
            article = intent.getParcelableExtra("EXTRA_ARTICLE_URL")!!
            adapter.showArticleDetails(article)
        }
    }



}

