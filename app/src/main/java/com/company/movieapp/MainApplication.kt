package com.company.movieapp

import android.app.Application
import com.company.movieapp.di.ApplicationComponent
import com.company.movieapp.di.DaggerApplicationComponent
import com.company.movieapp.repository.CommonMediaRepository

class MainApplication: Application() {

    lateinit var repository: CommonMediaRepository
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}