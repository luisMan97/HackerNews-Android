package com.example.hackernewscolegium.modules.news.repository

import com.example.hackernewscolegium.modules.news.entity.New

interface NewsOutputRepositoryInterface /*NewsPresenterInterface*/ {

    fun newsListDidFetch(newsList: ArrayList<New>)
    fun error()
    fun startLoading()
    fun stopLoading()

}