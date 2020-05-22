package com.perugu.goutham.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.perugu.goutham.newsapp.R
import com.perugu.goutham.newsapp.dagger.NewsAppComponentProvider
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.viewmodel.NewsFeedViewModelFactory
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import javax.inject.Inject

class NewsFeedsFragment: Fragment() {
    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var newsDatabase: NewsDb

    private val newsViewModel: NewsViewModel by activityViewModels {
        NewsAppComponentProvider.getNewsAppComponent(this.requireContext()).inject(this)
        NewsFeedViewModelFactory(okHttpClient, gson, newsDatabase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feeds_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsFeedsAdapter: NewsFeedsAdapter

        val feedsRecyclerView = requireView().findViewById<RecyclerView>(R.id.feeds_recycler_view)

        if (feedsRecyclerView.adapter == null){
            newsFeedsAdapter = NewsFeedsAdapter(arrayListOf())
        }else{
            newsFeedsAdapter = feedsRecyclerView.adapter as NewsFeedsAdapter
        }

        feedsRecyclerView.adapter = newsFeedsAdapter

        newsViewModel.newsFeedsLiveData.observe(viewLifecycleOwner, Observer {
            newsFeedsAdapter.updateList(it)
        })

    }
}

