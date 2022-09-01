package com.company.movieapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.company.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MovieApp)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
       /* if (savedInstanceState == null){

        }*/

        setUpBottomNavigationBar()

        //setOnNavigationItemReselected()

        /*currentNavController?.observe(this){navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->

                if (isMainFragment(destination)){
                    binding.navView.visibility = View.VISIBLE
                }else{
                    binding.navView.visibility = View.GONE
                }

            }
        }*/

    }

    override fun onBackPressed() {

        super.onBackPressed()

    }

 /*   override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }*/

    private fun isMainFragment(destination: NavDestination): Boolean =
        destination.id == R.id.home_fragment
                || destination.id == R.id.saved_fragment


    /*private fun FragmentManager.getCurrentNavigationFragment(): Fragment? =
        primaryNavigationFragment?.childFragmentManager?.fragments?.first()

    fun setOnNavigationItemReselected(){
        binding.navView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home -> supportFragmentManager.getCurrentNavigationFragment()
                    ?.setSmoothScrollToZero(R.id.recyclerView_list_movies)
                R.id.saved -> supportFragmentManager.getCurrentNavigationFragment()
                    ?.setSmoothScrollToZero(R.id.recyclerView_list_tvs)
            }
        }
    }
    }*/


    private fun setUpBottomNavigationBar(){
        val navView: BottomNavigationView = binding.navView
        val navController = binding.navHostFragmentActivityMain.getFragment<NavHostFragment>().navController
        //val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_fragment, R.id.saved_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)

       *//* val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        return super.onCreateOptionsMenu(menu)*//*

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu!!.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }
        return true
    }*/

    /*override fun onBackPressed() {
        val count: Int = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        }else{
            supportFragmentManager.popBackStack()
        }
    }*/

}