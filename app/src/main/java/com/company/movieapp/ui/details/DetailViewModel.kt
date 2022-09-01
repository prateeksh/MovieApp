package com.company.movieapp.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.movieapp.model.Media
import com.company.movieapp.model.Resource
import com.company.movieapp.repository.CommonMediaRepository
import com.company.movieapp.room.MediaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CommonMediaRepository, private val database: MediaDatabase): ViewModel() {

    val detailsMovie: MutableLiveData<Resource<Media>> =
        MutableLiveData(Resource(false, null, null))

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsMovie.postValue(detailsMovie.value!!.copy(isLoading = true))
            try {
                val response = repository.fetchMovieDetails(id)
                detailsMovie.postValue(detailsMovie.value!!.copy(isLoading = false, data = response))
            } catch (e: Exception) {
                detailsMovie.postValue(detailsMovie.value!!.copy(isLoading = false, error = e.message))
            }
        }
    }


    val detailsTv: MutableLiveData<Resource<Media>> =
        MutableLiveData(Resource(false, null, null))

    fun fetchTvDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            detailsTv.postValue(detailsTv.value!!.copy(isLoading = true))
            try {
                val response = repository.fetchTvDetails(id)
                detailsTv.postValue(detailsTv.value!!.copy(isLoading = false, data = response))
            } catch (e: Exception) {
                detailsTv.postValue(detailsTv.value!!.copy(isLoading = false, error = e.message))
            }
        }
    }


    fun insertInDb(media: Media){
        viewModelScope.launch(Dispatchers.IO) {
            database.mediaDao().insertMedia(media)
        }
    }

    val dataExits =  MutableLiveData<Boolean>()

    fun checkDbForData(id: Int): MutableLiveData<Boolean> {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("Detail View Model", "checkDbForData: ", )
            dataExits.postValue(database.mediaDao().checkExistence(id.toString()))
        }
        return dataExits
    }
}