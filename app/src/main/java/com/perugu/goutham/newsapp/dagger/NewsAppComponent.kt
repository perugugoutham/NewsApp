package com.perugu.goutham.newsapp.dagger

import android.content.Context
import com.perugu.goutham.newsapp.view.NewsDetailsFragment
import com.perugu.goutham.newsapp.view.NewsFeedsFragment
import dagger.Component


class NewsAppComponentProvider{

    companion object {

        @Volatile
        private var INSTANCE: NewsAppComponent? = null

        fun getNewsAppComponent(context: Context): NewsAppComponent {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {

                val instance =  DaggerNewsAppComponent.builder().newsAppModule(NewsAppModule(context)).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

@Component(modules = [NewsAppModule::class])
interface NewsAppComponent {

    fun inject (newsFeedsFragment: NewsFeedsFragment)

    fun inject (newsDetailsFragment: NewsDetailsFragment)
}