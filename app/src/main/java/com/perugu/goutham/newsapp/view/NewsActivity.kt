package com.perugu.goutham.newsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.gson.Gson
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import com.perugu.goutham.newsapp.R
import okhttp3.OkHttpClient

class NewsActivity : AppCompatActivity() {

    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient()

        val gson = Gson()

        newsViewModel.fetchNewsFeeds(okHttpClient, gson)
    }
}
