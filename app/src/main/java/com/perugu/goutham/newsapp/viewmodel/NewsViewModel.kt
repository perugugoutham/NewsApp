package com.perugu.goutham.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.repository.NetworkRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val networkRequestRepository: NetworkRequestRepository
) : ViewModel() {

    val newsFeedsLiveData: LiveData<List<Article>>
    get() = networkRequestRepository.newsDb.newsFeedDao().getNewsFeeds()

    fun fetchNewsFeeds() {
        viewModelScope.launch(Dispatchers.IO){
            networkRequestRepository.fetchNewsFeeds()
        }
    }

}