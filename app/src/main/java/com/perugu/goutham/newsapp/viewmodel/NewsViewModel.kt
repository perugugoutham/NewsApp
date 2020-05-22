package com.perugu.goutham.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val selectedArticle: MutableLiveData<Article> = MutableLiveData()

    fun fetchNewsFeeds() {
        networkRequestRepository.fetchNewsFeeds(viewModelScope)
    }

    fun updateSelectedArticle(article: Article){
        selectedArticle.postValue(article)
    }

}