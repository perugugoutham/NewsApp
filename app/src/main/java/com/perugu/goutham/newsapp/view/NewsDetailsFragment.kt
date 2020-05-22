package com.perugu.goutham.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.perugu.goutham.newsapp.R
import com.perugu.goutham.newsapp.dagger.NewsAppComponentProvider
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.viewmodel.NewsFeedViewModelFactory
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsDetailsFragment: Fragment() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var newsDatabase: NewsDb

    @Inject
    lateinit var picassoClient: Picasso

    private val newsViewModel: NewsViewModel by activityViewModels {
        NewsAppComponentProvider.getNewsAppComponent(this.requireContext()).inject(this)
        NewsFeedViewModelFactory(okHttpClient, gson, newsDatabase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NewsAppComponentProvider.getNewsAppComponent(this.requireContext()).inject(this)

        view.findViewById<ImageView>(R.id.back_button).setOnClickListener {
            findNavController().popBackStack()
        }

        newsViewModel.selectedArticle.observe(viewLifecycleOwner, Observer {
            requireView().findViewById<TextView>(R.id.description).text = it.description
            requireView().findViewById<TextView>(R.id.title).text = it.title
            requireView().findViewById<TextView>(R.id.source).text = it.source.name

            val simpleDateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.US)
            requireView().findViewById<TextView>(R.id.date).text = simpleDateFormat.format(it.publishedAt)

            picassoClient.load(it.urlToImage).into(requireView().findViewById<ImageView>(R.id.thumbnail_image))
        })
    }


}