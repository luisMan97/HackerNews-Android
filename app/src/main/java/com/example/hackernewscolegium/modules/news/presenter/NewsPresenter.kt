package com.example.hackernewscolegium.modules.news.presenter

import android.app.Activity
import android.content.Context
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.interactor.NewsInteractor
import com.example.hackernewscolegium.modules.news.repository.NewsOutputRepositoryInterface
import com.example.hackernewscolegium.modules.news.router.NewsRouting

class NewsPresenter(var context: Context, var view: NewsViewInterface?): NewsOutputRepositoryInterface {

    private var interactor: NewsInteractor? = NewsInteractor(context, this)
    var routing: NewsRouting? = NewsRouting(view as Activity)

    fun onDestroy() {
        view = null
        interactor?.unregister()
        interactor = null
        routing?.unregister()
        routing = null
    }

    fun removeNew(newList: ArrayList<New>) {
        interactor?.removeNew(newList)
    }

    fun getNewsList() {
        interactor?.getNewsList()
    }

    fun showNewSelection(new: New) {
        routing?.presentNewSelection(new)
    }

    override fun startLoading() {
        view?.startLoading()
    }

    override fun stopLoading() {
        view?.stopLoading()
    }

    override fun newsListDidFetch(newsList: ArrayList<New>) {
        view?.showNews(newsList)
    }

    override fun error() {
        view?.showError()
    }

}