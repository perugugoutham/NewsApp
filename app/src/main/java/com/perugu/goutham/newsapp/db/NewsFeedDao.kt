package com.perugu.goutham.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.perugu.goutham.newsapp.Article

@Dao
interface NewsFeedDao {

    @Insert(onConflict = REPLACE)
    suspend fun storeNewsFeeds(articles: List<Article>)

    @Query("SELECT * from Articles ORDER BY  publishedAt DESC")
    fun getNewsFeeds(): LiveData<List<Article>>

    @Query("SELECT * from Articles WHERE url=:id")
    fun getArticle(id: String): LiveData<Article>

}