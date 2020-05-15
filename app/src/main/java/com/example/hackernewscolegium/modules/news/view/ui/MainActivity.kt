package com.example.hackernewscolegium.modules.news.view.ui

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewscolegium.R
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.entity.then
import com.example.hackernewscolegium.modules.news.presenter.NewsPresenter
import com.example.hackernewscolegium.modules.news.presenter.NewsViewInterface
import com.example.hackernewscolegium.modules.news.view.adapter.NewsAdapter
import com.example.hackernewscolegium.modules.news.view.adapter.NewsListener
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsViewInterface, NewsListener {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var presenter: NewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = NewsPresenter(this)
        setupAdapter()
        setupItemTouchHelper()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.getNewsList()
        }
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
        newsAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                rlEmptyView.visibility = (newsAdapter.itemCount == 0) then View.VISIBLE ?: View.GONE
            }
        })
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwiped(viewHolder)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create().decorate()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvNews)
    }

    private fun onSwiped(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val titleOfSnackbar = newsAdapter.getTitleOfITem(position)
        newsAdapter.deleteItem(position)
        Snackbar.make(rvNews, titleOfSnackbar, Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                newsAdapter.undoItem(position)
            }.show()
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

}
