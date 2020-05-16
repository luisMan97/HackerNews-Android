package com.example.hackernewscolegium.utils.helpers

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewscolegium.R
import com.example.hackernewscolegium.modules.news.view.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

typealias DeleteItemHelperCallback = () -> (Unit)

class DeleteItemHelper(private val newsAdapter: NewsAdapter, private val recyclerView: RecyclerView, private val callback: DeleteItemHelperCallback) {

    fun setup(context: Context): ItemTouchHelper {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwiped(viewHolder)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create().decorate()
            }
        }

        return ItemTouchHelper(itemTouchHelperCallback)
    }


    private fun onSwiped(viewHolder: RecyclerView.ViewHolder) {
        val position = viewHolder.adapterPosition
        val titleOfSnackbar = newsAdapter.getTitleOfITem(position)
        newsAdapter.deleteItem(position)
        Snackbar.make(recyclerView, titleOfSnackbar, Snackbar.LENGTH_LONG).addCallback(object: Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                callback()
            }
        }).setAction("Undo") {
            newsAdapter.undoItem(position)
        }.show()
    }

}
