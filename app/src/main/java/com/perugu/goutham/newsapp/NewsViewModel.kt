package com.perugu.goutham.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class NewsViewModel: ViewModel() {

    fun fetchNewsFeeds(okHttpClient: OkHttpClient) {
        viewModelScope.launch(Dispatchers.IO){
            NetworkRequestRepository().fetchNewsFeeds(okHttpClient)
        }
    }

}