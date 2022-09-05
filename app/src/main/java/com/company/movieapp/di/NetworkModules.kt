package com.company.movieapp.di

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.company.movieapp.BuildConfig
import com.company.movieapp.R
import com.company.movieapp.api.ApiService
import com.company.movieapp.model.Media
import com.company.movieapp.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModules {

    @Singleton
    @Provides
    fun providesRetrofit() :Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(getClient(addApiKey = true))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesMovies(retrofit: Retrofit): ApiService{

        return retrofit.create(ApiService::class.java)
    }

   /* private val moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(Media::class.java,"media_type")
                .withSubtype(Media.Movies::class.java, "movies")
                .withSubtype(Media.TvShows::class.java, "tv")
        )
        .add(KotlinJsonAdapterFactory())
        .build()*/



    private fun getClient(addApiKey: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (addApiKey) {
            builder.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter(
                    "api_key",
                    BuildConfig.API_KEY
                ).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
        }
        return builder.build()
    }
}