package com.example.hackernewscolegium.apimanager

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIManager {

    private val urlApi = "http://hn.algolia.com/api/v1/"

    fun getClientService(): ApiService {
        val authInterceptor = Interceptor {
            val url = it.request().url().newBuilder()
                .addQueryParameter("format", "json")
                .build()

            val newRequest = it.request()
                .newBuilder()
                .url(url)
                .build()

            it.proceed(newRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(urlApi)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}