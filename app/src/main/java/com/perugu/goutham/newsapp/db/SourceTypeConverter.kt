package com.perugu.goutham.newsapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.perugu.goutham.newsapp.Source
import java.text.SimpleDateFormat
import java.util.*


class SourceTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String{
        val gson = Gson()
        val json = gson.toJson(source)
        return json
    }

    @TypeConverter
    fun stringToSource(json: String): Source{
        val gson = Gson()
        val source = gson.fromJson<Source>(json, Source::class.java)
        return source
    }

    @TypeConverter
    fun stringToDateObject(string: String): Date?{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US)
        return simpleDateFormat.parse(string)
    }

    @TypeConverter
    fun dateToString(date: Date): String{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.US)
        return simpleDateFormat.format(date)
    }
}