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

    val newsFeedsLiveData: LiveData<List<Article>>
        get() = networkRequestRepository.newsDb.newsFeedDao().getNewsFeeds()

    private val loaderState = MutableLiveData<LoaderState>(LoaderState.LOADING)

    init {
        fetchNewsFeeds()
    }

    fun fetchNewsFeeds() {
        networkRequestRepository.fetchNewsFeeds(viewModelScope, loaderState)
    }

    fun getArticle(id: String): LiveData<Article> {
        return networkRequestRepository.newsDb.newsFeedDao().getArticle(id)
    }

    fun getLoaderState(): LiveData<LoaderState> {
        return loaderState
    }

}

enum class LoaderState{
    LOADING,
    SUCCESS,
    FAILED
}