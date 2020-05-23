package com.perugu.goutham.newsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class NewsFeedsAdapter(
    private var articles: List<Article>,
    private val iTalkToFragment: ITalkToFragment,
    private val picassoClient: Picasso,
    private val dateFormat: SimpleDateFormat
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

        holder.source.text = article.source?.name

        if (article.publishedAt != null){
            holder.date.text = dateFormat.format(article.publishedAt)
        }

        holder.itemView.setOnClickListener {
            iTalkToFragment.onNewsFeedClicked(article.url)
        }
    }

    fun updateList(newArticles: List<Article>) {
        val oldArticles = this.articles
        this.articles = newArticles
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldArticles[oldItemPosition].url == newArticles[newItemPosition].url
            }

            override fun getOldListSize(): Int {
                return oldArticles.size
            }

            override fun getNewListSize(): Int {
                return newArticles.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldArticle = oldArticles[oldItemPosition]
                val newArticle = newArticles[newItemPosition]

                return oldArticle.title == newArticle.title
                        && oldArticle.author == newArticle.author
                        && oldArticle.content == newArticle.content
                        && oldArticle.description == newArticle.description
                        && oldArticle.publishedAt == newArticle.publishedAt
                        && oldArticle.source == newArticle.source
                        && oldArticle.url == newArticle.url
                        && oldArticle.urlToImage == newArticle.urlToImage
            }

        }, true)

        diffResult.dispatchUpdatesTo(this)

    }


    inner class NewsFeedViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thumbnailImage = view.findViewById<ImageView>(R.id.thumbail_image)
        val title = view.findViewById<TextView>(R.id.feed_title)
        val source = view.findViewById<TextView>(R.id.source)
        val date = view.findViewById<TextView>(R.id.date)
    }

}