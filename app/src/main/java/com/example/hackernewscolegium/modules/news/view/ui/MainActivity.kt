package com.example.hackernewscolegium.modules.news.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernewscolegium.R
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.entity.then
import com.example.hackernewscolegium.modules.news.presenter.NewsPresenter
import com.example.hackernewscolegium.modules.news.presenter.NewsViewInterface
import com.example.hackernewscolegium.modules.news.view.adapter.NewsAdapter
import com.example.hackernewscolegium.modules.news.view.adapter.NewsListener
import com.example.hackernewscolegium.utils.helpers.DeleteItemHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsViewInterface, NewsListener {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var presenter: NewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = NewsPresenter(this@MainActivity, this)
        setupAdapter()
        setupItemTouchHelper()
        setupSwipeRefreshLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun setupAdapter() {
        newsAdapter = NewsAdapter(this)
        rvNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelper = DeleteItemHelper(newsAdapter, rvNews) {
            presenter.removeNew(newsAdapter.getListOfNews())
        }.setup(this@MainActivity)
        itemTouchHelper.attachToRecyclerView(rvNews)
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.getNewsList()
        }
    }

    // Presenter Methods

    override fun startLoading() {
        rlBaseNews.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        rlBaseNews.visibility = View.INVISIBLE
    }

    override fun showNews(news: List<New>) {
        newsAdapter.updateData(news)
    }

    override fun showError() {

    }

    // NewsAdapter Methods

    override fun onNewClicked(new: New, position: Int) {
        presenter.showNewSelection(new)
    }

    override fun onChangedItemCount(itemCountIsEmpty: Boolean) {
        rlEmptyView.visibility = itemCountIsEmpty then View.VISIBLE ?: View.GONE
    }

}
