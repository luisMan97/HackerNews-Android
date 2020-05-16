package com.example.hackernewscolegium.modules.news.view.adapter

import com.example.hackernewscolegium.modules.news.entity.New

interface NewsListener {
    fun onNewClicked(new: New, position: Int)
    fun onChangedItemCount(itemCountIsEmpty: Boolean)
}