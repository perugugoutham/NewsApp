package com.perugu.goutham.newsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perugu.goutham.newsapp.Article
import com.perugu.goutham.newsapp.R

class NewsFeedsAdapter(var articles: List<Article>): RecyclerView.Adapter<NewsFeedsAdapter.NewsFeedViewHolder>() {

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
        holder.title.text = article.title

    }

    fun updateList(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }


    inner class NewsFeedViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.title)
    }
}