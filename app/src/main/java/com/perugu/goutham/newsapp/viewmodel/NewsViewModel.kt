package com.perugu.goutham.newsapp.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.repository.NetworkRequestRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val networkRequestRepository: NetworkRequestRepository
) : ViewModel() {

    private var coroutineDispatcher = Dispatchers.IO

    init {
        fetchNewsFeeds()
    }

    val newsFeedsLiveData: LiveData<List<Article>>
    get() = networkRequestRepository.newsDb.newsFeedDao().getNewsFeeds()

    fun fetchNewsFeeds() {
        viewModelScope.launch(coroutineDispatcher){
            networkRequestRepository.fetchNewsFeeds()
        }
    }

    @VisibleForTesting
    fun setCoRoutineDispacher(coroutineDispatcher: CoroutineDispatcher){
        this.coroutineDispatcher = coroutineDispatcher
    }

}