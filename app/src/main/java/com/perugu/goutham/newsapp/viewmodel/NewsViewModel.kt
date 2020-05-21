package com.perugu.goutham.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.perugu.goutham.newsapp.repository.NetworkRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class NewsViewModel: ViewModel() {

    fun fetchNewsFeeds(okHttpClient: OkHttpClient, gson: Gson) {
        viewModelScope.launch(Dispatchers.IO){
            NetworkRequestRepository().fetchNewsFeeds(okHttpClient, gson)
        }
    }

}