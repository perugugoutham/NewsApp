package com.perugu.goutham.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.repository.NetworkRequestRepository
import okhttp3.OkHttpClient

class NewsFeedViewModelFactory(val okHttpClient: OkHttpClient, val gson: Gson, val newsDb: NewsDb): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val networkRequestRepository = NetworkRequestRepository(okHttpClient, gson, newsDb)
        return NewsViewModel(networkRequestRepository) as T
    }
}