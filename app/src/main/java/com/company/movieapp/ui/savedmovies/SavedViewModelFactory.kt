package com.company.movieapp.ui.savedmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.repository.CommonMediaRepository
import com.company.movieapp.repository.SavedMoviesRepository
import com.company.movieapp.room.MediaDatabase
import com.company.movieapp.ui.home.HomeViewModel
import javax.inject.Inject

class SavedViewModelFactory @Inject constructor(private val repository: SavedMoviesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedMoviesViewModel(repository) as T
    }
}