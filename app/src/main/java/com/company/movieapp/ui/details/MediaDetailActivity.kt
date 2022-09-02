package com.company.movieapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.MainApplication
import com.company.movieapp.R
import com.company.movieapp.databinding.ActivityMediaDetailBinding
import com.company.movieapp.databinding.DetailBottomSheetBinding
import com.company.movieapp.model.Media
import com.company.movieapp.utils.*
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MediaDetailActivity : AppCompatActivity() {

    val id: Int
        get() = intent.getIntExtra("id", 0)

    val title: String?
        get() = intent.getStringExtra("title")

    private lateinit var binding: ActivityMediaDetailBinding

    @Inject
    lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MovieApp)
        super.onCreate(savedInstanceState)

        binding = ActivityMediaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setUpViewModel()
        fetchData()

    }


    private fun setUpViewModel(){
        (application as MainApplication).applicationComponent.injectDetailActivity(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        if (title == Constants.TV){

            viewModel.detailsTv.observe(this) {
                if (it.isLoading && it.data == null) {
                    showLoader(true)
                } else if (it.data != null) {
                    showLoader(false)
                    showDetailsDialog(it.data)
                }
            }


        }else{

            viewModel.detailsMovie.observe(this) {
                if (it.isLoading && it.data == null) {
                    showLoader(true)
                } else if (it.data != null) {
                    showLoader(false)
                    showDetailsDialog(it.data)
                }
            }


        }
        /*viewModel.checkDbForData(id!!)

        viewModel.dataExits.observe(viewLifecycleOwner, Observer {

            Log.e(TAG, "setUpViewModel: $it", )

            if (it){
                binding!!.favorite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.heart_24,
                        null
                    )
                )
            }else{
                binding!!.favorite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.heart_outline,
                        null
                    )
                )
            }
        })*/

    }


    private fun fetchData(){
        if (title == Constants.TV){
            Log.e(DetailBottomSheet.TAG, "fetchData: $title", )
            if (id != 0){
                viewModel.fetchTvDetails(id)
            }

        }else {
            Log.e(DetailBottomSheet.TAG, "fetchData: $title", )
            if (id != 0) {
                viewModel.fetchMovieDetails(id)
            }
        }
    }


    private fun showLoader(flag: Boolean) {
        if (flag) {
            binding.loader.root.show()

        } else {
            binding.loader.root.hide()
        }
    }

    private fun checkDb(): Boolean{

        var returnType: Boolean? = null
        viewModel.dataExits.observe(this, Observer {

            returnType = it
            Log.e(DetailBottomSheet.TAG, "checkDb: $it", )

        })
        return returnType!!
    }


    private fun showDetailsDialog(details: Media){

        val titleText: String = if (details.title != null){
            details.title!!
        }else{
            details.name!!

        }

        /* binding!!.favorite.setOnClickListener {
             viewModel.checkDbForData(id!!)
             if (!checkDb()) {
                 viewModel.insertInDb(details)
                 binding!!.favorite.setImageDrawable(
                     ContextCompat.getDrawable(requireContext(), R.drawable.heart_24)
                 )
                 Toast.makeText(requireContext(), "Inserted $titleText", Toast.LENGTH_LONG).show()
             }else{
                 Toast.makeText(requireContext(), "Already in database", Toast.LENGTH_LONG).show()
             }
         }*/


        val img = binding.imageView
        val ratings = binding.ratingBar
        val description = binding.overview



        Log.e(DetailBottomSheet.TAG, "showDetailsDialog: $titleText", )
        var isShow = true
        var scrollRange = -1
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                binding.collapsingToolbar.title = titleText

                isShow = true
            } else if (isShow){
                binding.collapsingToolbar.title = " "
                isShow = false
            }
        })

        ratings.rating = convertRatings(details.voteAverage!!)
        description.text = details.overview

        Picasso
            .get()
            .load(Constants.DETAIL_IMAGE_URL + details.posterPath)
            .fit()
            .into(img)


        if(details.releaseDate == null){
            binding.year.text = getReleaseYear(details.firstAirDate!!)
        }else
            binding.year.text = getReleaseYear(details.releaseDate!!)


        val countries = StringBuilder()
        for (details in details.productionCountries) {
            countries.append(details.iso31661).append(" ")
        }

        //binding!!.country.text = countries
        val genres: ArrayList<Int> = arrayListOf()
        for (data in details.genres){
            genres.add(data.id!!)
        }
        binding.geners.text = getGenresText(genres)
        if (details.runTime == null ){
            val appendSeasonsInfo = StringBuilder()
            //binding!!.length.text = "Seasons"
            binding.lengthMovie.text = appendSeasonsInfo.append(details.numberOfSeasons.toString()).append(" ").append("Season")
        }else{

            val appendMinutes = StringBuilder()
            binding.lengthMovie.text = appendMinutes.append(details.runTime).append(" ").append("minutes")
        }
    }

    private fun convertRatings(ratings: Double): Float{

        val rate = ratings/2
        return rate.toFloat()
    }
}