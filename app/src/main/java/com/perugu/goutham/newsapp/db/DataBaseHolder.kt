package com.perugu.goutham.newsapp.db

import android.content.Context
import androidx.room.Room

class DataBaseHolder {

    companion object {

        @Volatile
        private var INSTANCE: NewsDb? = null

        fun getDatabase(context: Context): NewsDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDb::class.java,
                    "news_feed_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}