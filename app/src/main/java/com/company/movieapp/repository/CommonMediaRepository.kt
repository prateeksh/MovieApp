package com.company.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.company.movieapp.api.ApiService
import com.company.movieapp.model.CommonData
import com.company.movieapp.model.Media
import com.company.movieapp.paging.*
import com.company.movieapp.utils.NetworkUtils
import javax.inject.Inject

class CommonMediaRepository @Inject constructor(
    private val movieService: ApiService
    ) {

    private var upcomingCommonData: LiveData<PagingData<Media>>? = null
    private var popularCommonData:  LiveData<PagingData<Media>>? = null
    private var topRatedCommonData:  LiveData<PagingData<Media>>? = null


    private var popularTv: LiveData<PagingData<Media>>? = null
    private var onAirTv: LiveData<PagingData<Media>>? = null
    private var topRatedTv: LiveData<PagingData<Media>>? = null


    private var trendingData: LiveData<CommonData<Media>>? = null

    fun getUpcomingMoviesData(): LiveData<PagingData<Media>>? {
        upcomingCommonData = Pager(
                config = PagingConfig(pageSize = 20, maxSize = 100),
                pagingSourceFactory = {UpcomingMoviePagingSource(movieService)}
            ).liveData

        return upcomingCommonData
    }

    fun getTopRatedMoviesData(): LiveData<PagingData<Media>>? {
        topRatedCommonData = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {TopRatedMoviePagingSource(movieService)}
        ).liveData

        return topRatedCommonData
    }

    fun getPopularMoviesData(): LiveData<PagingData<Media>>? {

        popularCommonData = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {PopularMoviePagingSource(movieService)}
        ).liveData

        return popularCommonData
    }

    fun getPopularTvData(): LiveData<PagingData<Media>>? {
        popularTv =  Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {PopularTvPagingSource(movieService)}
        ).liveData

        return popularTv
    }

    fun getOnAirData(): LiveData<PagingData<Media>>? {
        onAirTv = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {OnAirTvPagingSource(movieService)}
        ).liveData

        return onAirTv
    }

    fun getTopRatedTvData(): LiveData<PagingData<Media>>? {
        topRatedTv = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {TopRatedTvPagingSource(movieService)}
        ).liveData

        return topRatedTv
    }

    suspend fun fetchTvDetails(id: Int)  = movieService.getTvDetails(id)

    suspend fun fetchMovieDetails(id: Int) = movieService.getMovieDetails(id)

    suspend fun getSearchResults(query: String, page: Int) = movieService.performTvSearch(query, 1)

    suspend fun getTrendingMedia() = movieService.getTrending()

}