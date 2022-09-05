package com.company.movieapp.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.repository.CommonMediaRepository
import com.company.movieapp.ui.search.SearchViewModel
import javax.inject.Inject

class PersonViewModelFactory @Inject constructor(private val repository: CommonMediaRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonViewModel(repository) as T
    }
}