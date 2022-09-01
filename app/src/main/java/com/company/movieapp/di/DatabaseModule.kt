package com.company.movieapp.di

import android.content.Context
import androidx.room.Room
import com.company.movieapp.room.MediaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesMediaDb(context: Context) : MediaDatabase{
        return Room.databaseBuilder(context, MediaDatabase::class.java,"MediaDb").build()
    }
}