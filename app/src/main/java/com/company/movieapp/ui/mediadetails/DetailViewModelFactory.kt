package com.company.movieapp.ui.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.repository.CommonMediaRepository
import com.company.movieapp.room.MediaDatabase
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(private val repository: CommonMediaRepository, val database: MediaDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository, database) as T
    }
}