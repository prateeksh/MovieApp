package com.company.movieapp.ui.person

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.MainApplication
import com.company.movieapp.adapter.SearchAdapter
import com.company.movieapp.adapter.SearchMovieViewAdapter
import com.company.movieapp.databinding.ActivityPersonDetailsBinding
import com.company.movieapp.ui.mediadetails.MediaDetailActivity
import com.company.movieapp.utils.hide
import com.company.movieapp.utils.show
import javax.inject.Inject

private const val TAG = "PersonDetailsActivity"
class PersonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonDetailsBinding
    val id: Int
        get() = intent.getIntExtra("id", 0)

    val title: String?
        get() = intent.getStringExtra("title")



    @Inject
    lateinit var viewModelFactory: PersonViewModelFactory
    private lateinit var viewModel: PersonViewModel


    private lateinit var searchAdapter: SearchMovieViewAdapter
    private lateinit var searchRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MainApplication).applicationComponent.injectPerson(this@PersonDetailsActivity)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[PersonViewModel::class.java]
        //supportActionBar?.hide()

        setupUI()
        setUpViewModel()
    }


    private fun setupUI() {
        binding.toolbarPerson.setNavigationOnClickListener { finish() }

        if (id != 0){
            viewModel.fetchPersonResults(id)
            Log.e(TAG, "setupUI: $id", )
            updateUI()
        }
    }

    private fun updateUI(){
        searchRecycler = binding.moviesRecycler
        searchRecycler.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,false)

        //searchRecycler.setHasFixedSize(true)

    }

    private fun setUpViewModel(){
        viewModel.personResultsLoading.observe(this) { loading ->
            val searchResults = viewModel.personResults.value
            if (loading && searchResults == null) {
                binding.loaderPerson.show()
            } else {
                binding.loaderPerson.hide()
            }
        }
        viewModel.personResults.observe(this) {
            if(it != null) {
                Log.e(TAG, "setUpViewModel: ${it.id}",)
                searchAdapter = SearchMovieViewAdapter(it)
                searchRecycler.adapter = searchAdapter

                searchAdapter.onItemClick = { itemId: Int, itemTitle: String ->
                    val intent = Intent(this, MediaDetailActivity::class.java)
                    intent.putExtra("id", itemId)
                    intent.putExtra("title", itemTitle)
                    startActivity(intent)
                }
            }
        }
    }
}