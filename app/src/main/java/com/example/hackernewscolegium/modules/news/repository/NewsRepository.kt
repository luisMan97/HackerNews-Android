package com.example.hackernewscolegium.modules.news.repository

import android.content.Context
import android.util.Log
import com.example.hackernewscolegium.apimanager.APIManager
import com.example.hackernewscolegium.modules.news.entity.New
import com.example.hackernewscolegium.utils.helpers.SharedPreference
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(var context: Context, var presenter: NewsOutputRepositoryInterface?) {

    private var preferences = SharedPreference(context)

    fun getNews() {
        if (getNewsOfStorage()) {
            return
        }

        val news: ArrayList<New> = ArrayList<New>()
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
                    val jsonObject = jsonElement.asJsonObject
                    val new = New(jsonObject)
                    news.add(new)
                }

                presenter?.newsListDidFetch(news)
            }
        })
    }

    fun removeNew(newList: ArrayList<New>) {
        preferences.save("new", newList)
    }

    private fun getNewsOfStorage(): Boolean {
        val type = object : TypeToken<List<New>>() {}.type
        val news = preferences.getParseArray<List<New>>("new", type)
        Log.e("NOSE", "error $news")
        news?.let {
            presenter?.newsListDidFetch(news as ArrayList<New>)
            return true
        }
        return false
    }

}