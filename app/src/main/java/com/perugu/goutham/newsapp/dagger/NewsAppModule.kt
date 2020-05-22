package com.perugu.goutham.newsapp.dagger

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.perugu.goutham.newsapp.db.NewsDb
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named


const val network_request_coroutine = "network_request_coroutine"

@Module
class NewsAppModule(private val context: Context) {

    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient {

        val cacheControlBuilder = CacheControl.Builder()
        cacheControlBuilder.minFresh(Integer.MAX_VALUE, TimeUnit.DAYS)
        cacheControlBuilder.maxStale(Integer.MAX_VALUE, TimeUnit.DAYS)

        val okHttpClient = OkHttpClient.Builder().cache(cache).build()
        return okHttpClient
    }

    @Provides
    fun provideDiskCache(): Cache {
        return Cache(File(context.filesDir, "images"), 1024*1024*3)
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

    @Provides
    @Named(network_request_coroutine)
    fun provideNetworkRequestCoRoutine(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun providePicasso(okHttpClient: OkHttpClient): Picasso {
        val picasso = Picasso
            .Builder(context)
            .downloader(
                OkHttp3Downloader(
                    okHttpClient
                )
            )
            .build()
        return picasso
    }
}