package com.perugu.goutham.newsapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.perugu.goutham.newsapp.Source


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
}