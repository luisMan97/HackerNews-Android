package com.example.hackernewscolegium.modules.news.interactor

//import com.example.hackernewscolegium.modules.news.entity.New
import android.content.Context
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.presenter.NewsPresenter
import com.example.hackernewscolegium.modules.news.repository.NewsOutputRepositoryInterface
import com.example.hackernewscolegium.modules.news.repository.NewsRepository

class NewsInteractor(var context: Context, var presenter: NewsOutputRepositoryInterface?) {

    private val newsRepository = NewsRepository(context, presenter)

    fun unregister() {
        presenter = null
    }

    fun getNewsList() {
        newsRepository.getNews()
    }

    fun removeNew(newList: ArrayList<New>) {
        newsRepository.removeNew(newList)
    }

}