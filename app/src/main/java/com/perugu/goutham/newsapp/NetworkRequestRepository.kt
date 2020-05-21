package com.perugu.goutham.newsapp

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkRequestRepository {

    suspend fun fetchNewsFeeds(okHttpClient: OkHttpClient) {
        val url = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=0a8de2e03c2b4231894e73d4943f2895"
        val requestBuilder = Request.Builder()
        requestBuilder.url(url)
        val response = okHttpClient.newCall(requestBuilder.build()).execute()
        if (response.isSuccessful){
            Log.e("NetworkResponse", "${response.body?.string()}")
        }
    }
}