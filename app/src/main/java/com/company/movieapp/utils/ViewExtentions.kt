package com.company.movieapp.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.show(){
        this.visibility = View.VISIBLE
    }

    fun View.hide(){
        this.visibility = View.GONE
    }

fun getReleaseYear(yearRec: String): String? {

    return if (yearRec == null) {
        null
    } else {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = format.parse(yearRec)
        val df = SimpleDateFormat("yyyy")
        val year = df.format(date)
        year
    }
}

fun convertRatings(ratings: Double): Float{

    val rate = ratings/2
    return rate.toFloat()
}