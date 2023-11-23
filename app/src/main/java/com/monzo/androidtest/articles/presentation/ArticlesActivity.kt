package com.monzo.androidtest.articles.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articledetails.presentation.ArticleDetailsActivity
import com.monzo.androidtest.articledetails.presentation.ArticleDetailsActivity.Companion.EXTRA_ARTICLE_URL_KEY
import com.monzo.androidtest.articles.presentation.adapter.ArticleAdapter

class ArticlesActivity : AppCompatActivity(), ArticleAdapter.OnArticleClickListener {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.articles_swiperefreshlayout)
        val recyclerView = findViewById<RecyclerView>(R.id.articles_recyclerview)

        setSupportActionBar(toolbar)

        viewModel = HeadlinesApp.fromArticlesModule(applicationContext).inject(this)

        adapter = ArticleAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(this) { state ->
            swipeRefreshLayout.isRefreshing = state.refreshing
            adapter.showArticles(state.articles)
        }
    }

    override fun onArticleClick(articleUrl: String) {
        val intent = Intent(this, ArticleDetailsActivity::class.java)
            .apply {
                putExtra(EXTRA_ARTICLE_URL_KEY, articleUrl)
            }
        startActivity(intent)
    }
}

