package com.company.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.company.movieapp.api.ApiService
import com.company.movieapp.model.Media
import com.company.movieapp.paging.*
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class CommonMediaRepository @Inject constructor(
    private val movieService: ApiService
) {

    private var upcomingCommonData: Flow<PagingData<Media>>? = null
    private var popularCommonData:  Flow<PagingData<Media>>? = null
    private var topRatedCommonData:  Flow<PagingData<Media>>? = null


    private var popularTv: Flow<PagingData<Media>>? = null
    private var onAirTv: Flow<PagingData<Media>>? = null
    private var topRatedTv: Flow<PagingData<Media>>? = null


    fun getUpcomingMoviesData(): Flow<PagingData<Media>>? {
        upcomingCommonData = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {UpcomingMoviePagingSource(movieService)}
        ).flow

        return upcomingCommonData
    }

    fun getTopRatedMoviesData(): Flow<PagingData<Media>>? {
        topRatedCommonData = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {TopRatedMoviePagingSource(movieService)}
        ).flow

        return topRatedCommonData
    }

    fun getPopularMoviesData(): Flow<PagingData<Media>>? {

        popularCommonData = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {PopularMoviePagingSource(movieService)}
        ).flow

        return popularCommonData
    }

    fun getPopularTvData(): Flow<PagingData<Media>>? {
        popularTv =  Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {PopularTvPagingSource(movieService)}
        ).flow

        return popularTv
    }

    fun getOnAirData(): Flow<PagingData<Media>>? {
        onAirTv = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {OnAirTvPagingSource(movieService)}
        ).flow

        return onAirTv
    }

    fun getTopRatedTvData(): Flow<PagingData<Media>>? {
        topRatedTv = Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {TopRatedTvPagingSource(movieService)}
        ).flow

        return topRatedTv
    }

    suspend fun fetchTvDetails(id: Int)  = movieService.getTvDetails(id)

    suspend fun fetchMovieDetails(id: Int) = movieService.getMovieDetails(id)

    suspend fun getSearchResults(query: String) = movieService.performSearch(query, 1)

    suspend fun getTrendingMedia() = movieService.getTrending()

    suspend fun getRecommendedTv(id: Int) = movieService.getSimilarTv(id)

    suspend fun getRecommendedMovie(id: Int) = movieService.getSimilarMovie(id)

    suspend fun fetchPersonDetails(id: Int) = movieService.getPersonMovieDetail(id)

}