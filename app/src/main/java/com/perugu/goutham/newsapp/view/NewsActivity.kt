package com.perugu.goutham.newsapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.perugu.goutham.newsapp.R
import com.perugu.goutham.newsapp.dagger.NewsAppComponentProvider
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.viewmodel.NewsFeedViewModelFactory
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import javax.inject.Inject


class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var newsDatabase: NewsDb

    private val newsViewModel: NewsViewModel by viewModels {
        NewsAppComponentProvider.getNewsAppComponent(this).inject(this)
        NewsFeedViewModelFactory(okHttpClient, gson, newsDatabase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsViewModel.fetchNewsFeeds()

        newsViewModel.newsFeedsLiveData.observe(this, Observer {
            it.forEach {
                Log.e("LiveData "," ${it.publishedAt.time}")
            }
        })
    }
}
