package com.monzo.androidtest.articles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article

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

        viewModel = HeadlinesApp.from(applicationContext).inject(this)

        adapter = ArticleAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(this) { state ->
            swipeRefreshLayout.isRefreshing = state.refreshing
            adapter.showArticles(state.articles)
        }
    }
    override fun onArticleClick(article: Article) {
        val intent = Intent(this, ArticleDetailsActivity::class.java)
        intent.putExtra( "ARTICLE", article)
        startActivity(intent)
    }
}

