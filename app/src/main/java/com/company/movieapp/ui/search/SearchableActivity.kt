package com.company.movieapp.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.MainApplication
import com.company.movieapp.ui.person.PersonDetailsActivity
import com.company.movieapp.adapter.SearchAdapter
import com.company.movieapp.databinding.ActivitySearchBinding
import com.company.movieapp.ui.mediadetails.MediaDetailActivity
import com.company.movieapp.utils.Constants
import com.company.movieapp.utils.hide
import com.company.movieapp.utils.show
import javax.inject.Inject

class SearchableActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory


    private lateinit var searchAdapter: SearchAdapter
    private lateinit var searchRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        (application as MainApplication).applicationComponent.injectSearch(this@SearchableActivity)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        //supportActionBar?.hide()

        setupUI()
        setUpViewModel()
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.clearSearchIcon.setOnClickListener {
            binding.searchTextInput.setText("")

        }
        binding.searchTextInput.addTextChangedListener {
            val query = it.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.fetchSearchResults(query)
            }
            updateUI()
        }
    }

    private fun updateUI(){
        searchRecycler = binding.searchRecycler
        searchRecycler.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,false)

        /*childAdapter = ChildAdapter()
        recyclerView.adapter = childAdapter*/
        searchRecycler.setHasFixedSize(true)

    }

    private fun setUpViewModel(){
        viewModel.searchResultsLoading.observe(this) { loading ->
            val searchResults = viewModel.searchResults.value
            if (loading && searchResults == null) {
                binding.searchResultsLoader.show()
            } else {
                binding.searchResultsLoader.hide()
            }
        }
        viewModel.searchResults.observe(this) {
            searchAdapter = SearchAdapter(it)
            searchRecycler.adapter = searchAdapter

            searchAdapter.onItemClick = { id: Int, title: String ->

                if (title != Constants.PERSON) {

                    val intent = Intent(this, MediaDetailActivity::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("title", title)
                    startActivity(intent)

                }else{

                    //move to person detail activity
                    /*val intent = Intent(this, PersonDetailsActivity::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("title", title)
                    startActivity(intent)*/

                }
            }
        }
    }
}