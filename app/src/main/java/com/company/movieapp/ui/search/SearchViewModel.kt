package com.company.movieapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.movieapp.model.Media
import com.company.movieapp.repository.CommonMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: CommonMediaRepository): ViewModel() {


    val searchResultsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val searchResults: MutableLiveData<List<Media>> = MutableLiveData()

    fun fetchSearchResults(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchResultsLoading.postValue(true)
            try {
                val response = repository.getSearchResults(query, 1)
                searchResults.postValue(response.results)
                searchResultsLoading.postValue(false)
            } catch (e: Exception) {
                searchResultsLoading.postValue(false)
            }
        }
    }
}