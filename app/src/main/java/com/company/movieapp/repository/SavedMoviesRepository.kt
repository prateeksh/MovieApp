package com.company.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.movieapp.api.ApiService
import com.company.movieapp.model.Media
import com.company.movieapp.room.MediaDatabase
import javax.inject.Inject

class SavedMoviesRepository @Inject constructor(val apiService: ApiService,
                                                val database: MediaDatabase) {

    private var listData = MutableLiveData<MutableList<Media>>()

    val savedList: LiveData<MutableList<Media>>
        get() = listData


    suspend fun getSavedMoviesFromDb(){
        listData.postValue( database.mediaDao().getMedia())
    }

}