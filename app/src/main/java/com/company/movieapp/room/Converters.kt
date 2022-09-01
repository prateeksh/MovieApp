package com.company.movieapp.room

import androidx.room.TypeConverter
import com.company.movieapp.model.subdataclass.Genres
import com.company.movieapp.model.subdataclass.ProductionCountries
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromCountriesString(value: String?): ArrayList<ProductionCountries?>? {
       val gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<ProductionCountries?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromCountriesArrayList(list: ArrayList<ProductionCountries?>?): String? {
        val type = object : TypeToken<ArrayList<ProductionCountries?>?>() {}.type
        val gson = Gson()
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromGenresString(value: String?): ArrayList<Genres?>? {
        val listType: Type = object : TypeToken<ArrayList<Genres?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromGenresArrayList(list: ArrayList<Genres?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromInteger(value: String?): ArrayList<Int?>? {
        val listType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIntegerList(list: ArrayList<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}