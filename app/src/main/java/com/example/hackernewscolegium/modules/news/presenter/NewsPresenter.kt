package com.example.hackernewscolegium.modules.news.presenter

import android.app.Activity
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.interactor.NewsInteractor
import com.example.hackernewscolegium.modules.news.repository.NewsOutputRepositoryInterface
import com.example.hackernewscolegium.modules.news.router.NewsRouting

class NewsPresenter(var view: NewsViewInterface?): NewsOutputRepositoryInterface {

    private var interactor: NewsInteractor? = NewsInteractor(this)
    var routing: NewsRouting? = NewsRouting(view as Activity)

    init {
        interactor?.getNewsList()
    }

    fun onDestroy() {
        view = null
        interactor?.unregister()
        interactor = null
        routing?.unregister()
        routing = null
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

    override fun newsListDidFetch(newList: ArrayList<New>) {
        view?.showNews(newList)
    }

    override fun error() {
        view?.showError()
    }

}