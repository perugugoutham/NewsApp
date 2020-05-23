package com.perugu.goutham.newsapp

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.perugu.goutham.newsapp.db.NewsDb
import com.perugu.goutham.newsapp.repository.NetworkRequestRepository
import com.perugu.goutham.newsapp.viewmodel.NewsViewModel
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class NewsAppTests {

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var newsDb: NewsDb

    @Before
    fun setUp(){

        val context = ApplicationProvider.getApplicationContext<Context>()

        newsDb = Room.inMemoryDatabaseBuilder(
            context,
            NewsDb::class.java
        ).build()

        val networkRequestRepository = NetworkRequestRepository(
            OkHttpClient(),
            Gson(),
            newsDb,
            Dispatchers.Unconfined
        )

        newsViewModel = NewsViewModel(networkRequestRepository)
    }

    @Test
    fun insertAndFetchFromDbTest(){
        newsViewModel.fetchNewsFeeds()
        val value = getValue(newsViewModel.newsFeedsLiveData)
        value.size shouldBe 10 //Asserting using infix helper that the total list size is 10 (as the n/w response return only top 10 headlines)

        //Just printing all author names
        value.forEach {
            println(it.author)
        }

    }

    @Test
    fun selectedAirtcleTest(){
        newsViewModel.fetchNewsFeeds()
        val value = getValue(newsViewModel.newsFeedsLiveData)
        val article = value[2]
        val selectedAirtcle = getValue(newsViewModel.getArticle(article.url))
        selectedAirtcle shouldBe article
    }

    @After
    fun closeDb(){
        newsDb.close()
    }

    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }

        }

        Handler(Looper.getMainLooper()).post {
            liveData.observeForever(observer)
        }

        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }

    infix fun <T> T.shouldBe(any: Any?) {
        TestCase.assertEquals(any, this)
    }
}
