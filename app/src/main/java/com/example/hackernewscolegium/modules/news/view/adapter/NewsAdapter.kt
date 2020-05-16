package com.example.hackernewscolegium.modules.news.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewscolegium.R
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.entity.then
import kotlinx.android.synthetic.main.activity_main.*

class NewsAdapter(private val newsListener: NewsListener): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var listNews = ArrayList<New>()
    private lateinit var deletedTemporalNew: New

    init {
        registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
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
                newsListener.onChangedItemCount(itemCount == 0)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_new, parent, false))

    override fun getItemCount() = listNews.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val new = listNews[position]
        holder.setData(new)
        holder.itemView.setOnClickListener {
            newsListener.onNewClicked(new, position)
        }
    }

    fun updateData(data: List<New>) {
        listNews.clear()
        listNews.addAll(data)
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int) {
        deletedTemporalNew = listNews[index]
        listNews.removeAt(index)
        notifyItemRemoved(index)
    }

    fun undoItem(index: Int) {
        listNews.add(index, deletedTemporalNew)
        notifyItemInserted(index)
    }

    fun getTitleOfITem(index: Int): String {
        return listNews[index].title.isEmpty() then listNews[index].storyTitle ?: listNews[index].title
    }

    fun getListOfNews(): ArrayList<New> {
        return listNews
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var new: New
        private val tvNewTitle: TextView = itemView.findViewById(R.id.tvItemNewTitle)
        private val tvNewAuthor: TextView = itemView.findViewById(R.id.tvItemNewAuthor)
        private val tvNewCreatedAt: TextView = itemView.findViewById(R.id.tvItemNewCreatedAt)

        fun setData(new: New) {
            this.new = new

            tvNewTitle.text = new.title.isEmpty() then new.storyTitle ?: new.title
            tvNewAuthor.text = new.author
            tvNewCreatedAt.text = new.createdAt
        }

    }

}