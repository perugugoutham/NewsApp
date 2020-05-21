package com.perugu.goutham.newsapp.repository

import android.util.Log
import com.google.gson.Gson
import com.perugu.goutham.newsapp.NewsFeeds
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkRequestRepository {

    suspend fun fetchNewsFeeds(okHttpClient: OkHttpClient, gson: Gson) {
        val url = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=0a8de2e03c2b4231894e73d4943f2895"
        val requestBuilder = Request.Builder()
        requestBuilder.url(url)
        val response = okHttpClient.newCall(requestBuilder.build()).execute()
        if (response.isSuccessful){
            val responseString = response.body?.string()
            val newsFeeds = gson.fromJson<NewsFeeds>(responseString, NewsFeeds::class.java)
            newsFeeds.articles.forEach {
                Log.e("Perugu", "${it.author}")
            }
        }
    }
}