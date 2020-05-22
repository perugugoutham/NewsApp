package com.perugu.goutham.newsapp.dagger

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.perugu.goutham.newsapp.db.NewsDb
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class NewsAppModule(val context: Context) {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient()
        return okHttpClient
    }

    @Provides
    fun provideGson(): Gson {
        val gson = Gson()
        return gson
    }

    @Provides
    fun provideDataBase(): NewsDb {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDb::class.java,
            "news_feed_db"
        ).build()
    }
}