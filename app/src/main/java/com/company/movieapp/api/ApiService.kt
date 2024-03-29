package com.company.movieapp.api

import com.company.movieapp.model.CommonData
import com.company.movieapp.model.Media
import com.company.movieapp.model.Person
import com.company.movieapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): CommonData<Media>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): CommonData<Media>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): CommonData<Media>




    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int) : Media

    @GET("movie/{id}/similar&page=1")
    suspend fun getSimilarMovie(@Path("id") id: Int): Media




    @GET("tv/popular")
    suspend fun getPopularTv(@Query("page") page: Int): CommonData<Media>

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(@Query("page") page: Int): CommonData<Media>


    @GET("tv/on_the_air")
    suspend fun getOnAirTv(@Query("page") page: Int): CommonData<Media>


    @GET("tv/{id}")
    suspend fun getTvDetails(@Path("id") id: Int) : Media

    @GET("tv/{id}/similar&page=1")
    suspend fun getSimilarTv(@Path("id") id: Int): Media

    @GET("search/multi")
    suspend fun performSearch(
        @Query("query") query: String,
        @Query("page") page: Int): SearchResponse

    @GET("trending/all/week")
    suspend fun getTrending() : CommonData<Media>

    @GET("person/{id}/movie_credits")
    suspend fun getPersonMovieDetail(@Path("id") id:Int): Person
}