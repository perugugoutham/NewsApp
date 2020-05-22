package com.perugu.goutham.newsapp.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.NewsFeeds
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.viewmodel.LoaderState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkRequestRepository(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson,
    val newsDb: NewsDb,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    fun fetchNewsFeeds(
        viewModelScope: CoroutineScope,
        loaderState: MutableLiveData<LoaderState>
    ) {
        loaderState.postValue(LoaderState.LOADING)
        viewModelScope.launch (coroutineDispatcher){
            val url = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=0a8de2e03c2b4231894e73d4943f2895"
            val requestBuilder = Request.Builder()
            requestBuilder.url(url)
            try {
                val response = okHttpClient.newCall(requestBuilder.build()).execute()
                if (response.isSuccessful){
                    val responseString = response.body?.string()
                    val newsFeeds = gson.fromJson<NewsFeeds>(responseString, NewsFeeds::class.java)
                    loaderState.postValue(LoaderState.SUCCESS)
                    storeArticleInDb(newsFeeds.articles)
                }else {
                    loaderState.postValue(LoaderState.FAILED)
                }
            }
            catch (e: Exception){
                e.printStackTrace()
                loaderState.postValue(LoaderState.FAILED)
            }

        }

    }

    private suspend fun storeArticleInDb(articles: List<Article>) {
        newsDb.newsFeedDao().storeNewsFeeds(articles)
    }
}