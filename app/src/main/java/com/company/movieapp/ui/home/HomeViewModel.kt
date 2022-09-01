package com.company.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.company.movieapp.model.Media
import com.company.movieapp.repository.CommonMediaRepository

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

}