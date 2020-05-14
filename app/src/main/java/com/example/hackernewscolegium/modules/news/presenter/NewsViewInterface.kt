package com.example.hackernewscolegium.modules.news.presenter

import com.example.hackernewscolegium.modules.news.entity.New

interface NewsViewInterface {
    fun startLoading()
    fun stopLoading()
    fun showNews(news: List<New>)
    fun showError()
}