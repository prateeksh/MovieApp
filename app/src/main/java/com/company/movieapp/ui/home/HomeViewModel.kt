package com.company.movieapp.ui.home


import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.company.movieapp.model.Media
import com.company.movieapp.repository.CommonMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CommonMediaRepository) : ViewModel() {

    fun getPopularMovies() : LiveData<PagingData<Media>>? {
        return repository.getPopularMoviesData()?.cachedIn(viewModelScope)
    }

    fun getTopRatedMovies() : LiveData<PagingData<Media>>? {
        return repository.getTopRatedMoviesData()?.cachedIn(viewModelScope)
    }

    fun getUpComingMovies() : LiveData<PagingData<Media>>? {
        return repository.getUpcomingMoviesData()?.cachedIn(viewModelScope)
    }

    fun getPopularTv() : LiveData<PagingData<Media>>? {
        return repository.getPopularTvData()?.cachedIn(viewModelScope)
    }

    fun getTopRatedTv() : LiveData<PagingData<Media>>? {
        return repository.getTopRatedTvData()?.cachedIn(viewModelScope)
    }

    fun getOnAirTv() : LiveData<PagingData<Media>>? {
        return repository.getOnAirData()?.cachedIn(viewModelScope)
    }
    val trendingMedia: MutableLiveData<ArrayList<Media>> = MutableLiveData()

    fun getTrendingMedia() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTrendingMedia()
            trendingMedia.postValue(response.results)
        }
    }
}