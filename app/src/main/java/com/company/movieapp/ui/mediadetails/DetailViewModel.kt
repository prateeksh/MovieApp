package com.company.movieapp.ui.mediadetails

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


    val similarTv: MutableLiveData<Resource<Media>> =
        MutableLiveData(Resource(false, null, null))

    fun fetchSimilarTv(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            similarTv.postValue(similarTv.value!!.copy(isLoading = true))
            try {
                val response = repository.fetchTvDetails(id)
                similarTv.postValue(similarTv.value!!.copy(isLoading = false, data = response))
            } catch (e: Exception) {
                similarTv.postValue(similarTv.value!!.copy(isLoading = false, error = e.message))
            }
        }
    }



    val similarMovie: MutableLiveData<Resource<Media>> =
        MutableLiveData(Resource(false, null, null))

    fun fetchSimilarMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            similarMovie.postValue(similarMovie.value!!.copy(isLoading = true))
            try {
                val response = repository.fetchTvDetails(id)
                similarMovie.postValue(similarMovie.value!!.copy(isLoading = false, data = response))
            } catch (e: Exception) {
                similarMovie.postValue(similarMovie.value!!.copy(isLoading = false, error = e.message))
            }
        }
    }


    fun insertInDb(media: Media){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.mediaDao().insertMedia(media)
            }catch (e: Exception){
                e.message
            }

        }
    }

    val dataExits =  MutableLiveData<Boolean>()

    fun checkDbForData(id: Int): MutableLiveData<Boolean> {
        viewModelScope.launch(Dispatchers.IO) {
            dataExits.postValue(database.mediaDao().checkExistence(id.toString()))
        }
        return dataExits
    }
}