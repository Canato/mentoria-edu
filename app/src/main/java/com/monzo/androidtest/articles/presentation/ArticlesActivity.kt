package com.monzo.androidtest.articles.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articledetails.presentation.ArticleDetailsActivity
import com.monzo.androidtest.articledetails.presentation.ArticleDetailsActivity.Companion.EXTRA_ARTICLE_URL_KEY
import com.monzo.androidtest.articles.domain.Article
import com.monzo.androidtest.articles.presentation.adapter.ArticleAdapter
import com.monzo.androidtest.articles.presentation.ArticlesViewModel

class ArticlesActivity : AppCompatActivity(), ArticleAdapter.OnArticleClickListener {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticleAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        swipeRefreshLayout = findViewById(R.id.articles_swiperefreshlayout)
        val recyclerView = findViewById<RecyclerView>(R.id.articles_recyclerview)

        setSupportActionBar(toolbar)

        val articleUrl : String = intent.getStringExtra(EXTRA_ARTICLE_URL_KEY).toString()
        viewModel = HeadlinesApp.fromArticlesModule(applicationContext).inject(this, articleUrl)

        adapter = ArticleAdapter(this, this, object : ArticleAdapter.OnFavoriteClickListener {
            override fun onFavoriteClick(article: Article) {
                viewModel.toggleFavorite(article)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(this) { state ->
            swipeRefreshLayout.isRefreshing = state.refreshing
            adapter.showArticles(state.articles)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLatestArticles()
    }

    override fun onArticleClick(articleUrl: String) {
        val intent = Intent(this, ArticleDetailsActivity::class.java)
            .apply {
                putExtra(EXTRA_ARTICLE_URL_KEY, articleUrl)
            }
        startActivity(intent)
    }
}