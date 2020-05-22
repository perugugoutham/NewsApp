package com.perugu.goutham.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.R
import com.perugu.goutham.newsapp.dagger.NewsAppComponentProvider
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.viewmodel.NewsFeedViewModelFactory
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import javax.inject.Inject

class NewsFeedsFragment: Fragment(), ITalkToFragment {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var newsDatabase: NewsDb

    @Inject
    lateinit var picassoClient: Picasso

    private var newsFeedsAdapter: NewsFeedsAdapter?= null

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

        NewsAppComponentProvider.getNewsAppComponent(this.requireContext()).inject(this)

        val swipeRefreshLayout = requireView().findViewById<SwipeRefreshLayout>(R.id.swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            newsViewModel.fetchNewsFeeds()
        }

        val feedsRecyclerView = requireView().findViewById<RecyclerView>(R.id.feeds_recycler_view)

        if (newsFeedsAdapter == null){
            newsFeedsAdapter = NewsFeedsAdapter(arrayListOf(), this, picassoClient)
        }

        feedsRecyclerView.adapter = newsFeedsAdapter

        newsViewModel.newsFeedsLiveData.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            newsFeedsAdapter?.updateList(it)
        })

    }

    override fun onNewsFeedClicked(article: Article) {
        newsViewModel.updateSelectedArticle(article)
        findNavController().navigate(R.id.action_newsFeedsFragment_to_newsDetailsFragment)
    }
}

interface ITalkToFragment{
    fun onNewsFeedClicked(article: Article)
}

