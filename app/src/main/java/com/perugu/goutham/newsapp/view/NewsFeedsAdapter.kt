package com.perugu.goutham.newsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class NewsFeedsAdapter(
    private var articles: List<Article>,
    private val iTalkToFragment: ITalkToFragment,
    private val picassoClient: Picasso
): RecyclerView.Adapter<NewsFeedsAdapter.NewsFeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_feed_holde_layout, parent, false)
        return NewsFeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val article = articles[position]

        picassoClient.load(article.urlToImage)
            .into(holder.thumbnailImage)

        holder.title.text = article.title

        holder.source.text = article.source.name

        val simpleDateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.US)
        holder.date.text = simpleDateFormat.format(article.publishedAt)

        holder.itemView.setOnClickListener {
            iTalkToFragment.onNewsFeedClicked(article)
        }
    }

    fun updateList(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }


    inner class NewsFeedViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thumbnailImage = view.findViewById<ImageView>(R.id.thumbail_image)
        val title = view.findViewById<TextView>(R.id.feed_title)
        val source = view.findViewById<TextView>(R.id.source)
        val date = view.findViewById<TextView>(R.id.date)
    }

}