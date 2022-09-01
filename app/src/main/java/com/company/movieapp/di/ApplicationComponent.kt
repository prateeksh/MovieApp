package com.company.movieapp.di

import android.content.Context
import com.company.movieapp.ui.details.DetailBottomSheet
import com.company.movieapp.ui.home.HomeFragment
import com.company.movieapp.ui.savedmovies.SavedMoviesFragment
import com.company.movieapp.ui.search.SearchableActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModules::class, DatabaseModule::class])
interface ApplicationComponent {


    fun inject(mainActivity: HomeFragment)

    fun injectDetails(detailsActivity: DetailBottomSheet)

    fun injectSearch(search: SearchableActivity)

    fun injectSaved(savedMoviesFragment: SavedMoviesFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}