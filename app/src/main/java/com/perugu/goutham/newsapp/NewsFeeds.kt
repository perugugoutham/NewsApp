package com.perugu.goutham.newsapp

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.perugu.goutham.newsapp.db.SourceTypeConverter
import java.util.*

data class NewsFeeds(
    @SerializedName("articles")
    val articles: List<Article>,

    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int
)

@Entity(tableName = "Articles", primaryKeys = ["author", "content", "description", "publishedAt", "source", "title", "url", "urlToImage"])

/*There is no proper id in the data to maintain as primary key,
hence we are considering all fields as primarykey*/

@TypeConverters(SourceTypeConverter::class)
data class Article(

    @SerializedName("author")
    val author: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("publishedAt")
    val publishedAt: Date,

    @SerializedName("source")
    val source: Source,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val urlToImage: String
)

data class Source(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)