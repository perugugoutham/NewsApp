package com.perugu.goutham.newsapp.view

import android.content.Intent
import android.net.Uri
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
import com.perugu.goutham.newsapp.dagger.network_request_coroutine
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.viewmodel.NewsFeedViewModelFactory
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class NewsDetailsFragment: Fragment() {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var newsDatabase: NewsDb

    @Inject
    lateinit var picassoClient: Picasso

    @Inject
    @field:Named(network_request_coroutine)
    lateinit var networkRequestCoroutine: CoroutineDispatcher

    private val newsViewModel: NewsViewModel by activityViewModels {
        NewsAppComponentProvider.getNewsAppComponent(this.requireContext()).inject(this)
        NewsFeedViewModelFactory(okHttpClient, gson, newsDatabase, networkRequestCoroutine)
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
        val description = requireView().findViewById<TextView>(R.id.description)
        val title = requireView().findViewById<TextView>(R.id.title)
        val source = requireView().findViewById<TextView>(R.id.source)

        val simpleDateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.US)

        val date = requireView().findViewById<TextView>(R.id.date)

        val newsDetailsFragmentArgs = NewsDetailsFragmentArgs.fromBundle(arguments!!)

        newsViewModel.getArticle(newsDetailsFragmentArgs.selectId).observe(viewLifecycleOwner, Observer {article ->
            description.text = getString(R.string.click_for_more, article.description)
            title.text = article.title
            source.text = article.source?.name
            if (article.publishedAt != null){
                date.text = simpleDateFormat.format(article.publishedAt)
            }

            picassoClient.load(article.urlToImage).into(requireView().findViewById<ImageView>(R.id.thumbnail_image))
            description.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(browserIntent)
            }
        })
    }


}