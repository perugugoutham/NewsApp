package com.perugu.goutham.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.repository.NetworkRequestRepository

class NewsViewModel(
    private val networkRequestRepository: NetworkRequestRepository
) : ViewModel() {

    init {
        fetchNewsFeeds()
    }

    val newsFeedsLiveData: LiveData<List<Article>>
    get() = networkRequestRepository.newsDb.newsFeedDao().getNewsFeeds()

    fun fetchNewsFeeds() {
        networkRequestRepository.fetchNewsFeeds(viewModelScope)
    }

    fun getArticle(id: String): LiveData<Article> {
        return networkRequestRepository.newsDb.newsFeedDao().getArticle(id)
    }

}