package com.company.movieapp.model

data class Resource<T>(val isLoading: Boolean, val data: T?, val error: String?)

