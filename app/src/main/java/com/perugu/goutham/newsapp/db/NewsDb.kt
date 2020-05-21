package com.perugu.goutham.newsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perugu.goutham.newsapp.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDb: RoomDatabase() {
    abstract fun newsFeedDao(): NewsFeedDao
}