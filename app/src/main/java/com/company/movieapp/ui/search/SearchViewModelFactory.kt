package com.company.movieapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.repository.CommonMediaRepository
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(private val repository: CommonMediaRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}