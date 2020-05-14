package com.example.hackernewscolegium.modules.news.repository

import android.util.Log
import android.view.View
import com.example.hackernewscolegium.apimanager.APIManager
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.modules.news.presenter.NewsPresenter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(var presenter: NewsOutputRepositoryInterface?) {

    fun getNews() {
        var news: ArrayList<New> = ArrayList<New>()
        val apiService = APIManager().getClientService()
        val call = apiService.getNews()

        presenter?.startLoading()

        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                presenter?.stopLoading()

                Log.e("ERROR: ", t.message ?: "")
                t.stackTrace
                presenter?.error()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                presenter?.stopLoading()

                val offersJsonArray = response.body()?.getAsJsonArray("hits")
                offersJsonArray?.forEach { jsonElement: JsonElement ->
                    var jsonObject = jsonElement.asJsonObject
                    var new = New(jsonObject)
                    news.add(new)
                }

                presenter?.newsListDidFetch(news)
            }
        })
    }

}