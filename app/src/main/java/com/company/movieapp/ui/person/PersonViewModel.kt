package com.company.movieapp.ui.person

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.movieapp.model.Media
import com.company.movieapp.model.Person
import com.company.movieapp.repository.CommonMediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: CommonMediaRepository): ViewModel() {


     val personResultsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
     val personResults: MutableLiveData<Person> = MutableLiveData()

    fun fetchPersonResults(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            personResultsLoading.postValue(true)
            try {
                val response = repository.fetchPersonDetails(id)
                personResults.postValue(response)
                personResultsLoading.postValue(false)
            } catch (e: Exception) {
                personResultsLoading.postValue(false)
            }
        }
    }

}