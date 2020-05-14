package com.example.hackernewscolegium.modules.news.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewscolegium.R
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.entity.then
import com.example.hackernewscolegium.modules.news.presenter.NewsPresenter

class NewsAdapter(private val newsListener: NewsListener): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var listNews = ArrayList<New>()
    lateinit var presenter: NewsPresenter

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
        //listNews.clear()
        listNews.addAll(data)
        notifyDataSetChanged()
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