package com.company.movieapp.ui.savedmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.movieapp.model.Media
import com.company.movieapp.repository.CommonMediaRepository
import com.company.movieapp.repository.SavedMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedMoviesViewModel(val repository: SavedMoviesRepository) : ViewModel() {

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getSavedMoviesFromDb()
        }

    }

    val saved: LiveData<MutableList<Media>>
        get() = repository.savedList
}