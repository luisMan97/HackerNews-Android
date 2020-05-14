package com.example.hackernewscolegium.modules.news.router

import android.app.Activity
import android.content.Intent
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.view.ui.NewDetailActivity

class NewsRouting(var view: Activity?) {

    fun unregister() {
        view = null
    }

    fun presentNewSelection(new: New) {
        val intent = Intent(view, NewDetailActivity::class.java)
        intent.putExtra("URL", new.storyUrl)
        view?.startActivity(intent)
    }

}