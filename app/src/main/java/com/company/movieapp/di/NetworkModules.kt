package com.company.movieapp.di

import com.company.movieapp.BuildConfig
import com.company.movieapp.api.ApiService
import com.company.movieapp.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


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


    private fun getClient(addApiKey: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (addApiKey) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter(
                    "api_key", BuildConfig.API_KEY
                ).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
        }
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(interceptor)
        }

        builder.addNetworkInterceptor { chain: Interceptor.Chain ->
            val request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build()
            chain.proceed(request)
        }


        return builder.build()
    }


/*
    fun gfgHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(*//*our interceptor*//*)
        return builder.build()
    }*/
}