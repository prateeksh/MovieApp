package com.company.movieapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.company.movieapp.databinding.ActivityMainBinding
import com.company.movieapp.ui.home.HomeFragment
import com.company.movieapp.ui.savedmovies.SavedMoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val savedMoviesFragment = SavedMoviesFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //splashScreen.setKeepOnScreenCondition{true}
        setUpBottomNavigationBar()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setUpBottomNavigationBar(){
        val navView: BottomNavigationView = binding.navView

        var fragment = Fragment()

    /* fragmentManager.beginTransaction().apply {
            add(R.id.nav_host_fragment_activity_main, homeFragment, "home_frag")
            add(R.id.nav_host_fragment_activity_main, savedMoviesFragment, "save_movies").hide(savedMoviesFragment)
        }.commit()

        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_fragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment).commit()
                    homeFragment.onFirstDisplay()
                    activeFragment = homeFragment
                    true
                }
                R.id.saved_fragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(savedMoviesFragment).commit()
                    activeFragment = savedMoviesFragment
                    true
                }
                else -> false
            }
        }
*/
        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home_fragment -> {
                    fragment = HomeFragment()

                }
                R.id.saved_fragment -> {
                    fragment = SavedMoviesFragment()
                }
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
            true
        }

        navView.selectedItemId = R.id.home_fragment
    }
}