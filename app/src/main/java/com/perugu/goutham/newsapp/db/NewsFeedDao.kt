package com.perugu.goutham.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.perugu.goutham.newsapp.Article

@Dao
interface NewsFeedDao {

    @Insert(onConflict = IGNORE) //using IGNORE instead if REPLACE because in our case all fields together are considered primary keys
    suspend fun storeNewsFeeds(articles: List<Article>)

    @Query("SELECT * from Articles ORDER BY  publishedAt DESC")
    fun getNewsFeeds(): LiveData<List<Article>>

}