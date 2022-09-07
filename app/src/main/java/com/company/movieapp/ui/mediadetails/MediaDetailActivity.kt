package com.company.movieapp.ui.mediadetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.MainApplication
import com.company.movieapp.R
import com.company.movieapp.databinding.ActivityMediaDetailBinding
import com.company.movieapp.model.Media
import com.company.movieapp.model.subdataclass.Genres
import com.company.movieapp.utils.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import javax.inject.Inject

private const val TAG = "MediaDetailActivity"
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

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_detail)
        //setContentView(binding.root)
       // supportActionBar?.hide()

        if (savedInstanceState == null){
            setUpViewModel()
            fetchData()
        }
    }




    private fun setUpViewModel(){
        (application as MainApplication).applicationComponent.injectDetailActivity(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        if (title == Constants.TV ){

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
        viewModel.checkDbForData(id)

        viewModel.dataExits.observe(this, Observer {

            Log.e(TAG, "setUpViewModel: $it", )

            if (it){
                binding.favorite.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.heart_24,
                        null
                    )
                )
            }
        })

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

        Log.e(TAG, "showDetailsDialog: $details", )
        val titleText: String = if (details.title != null){
            details.title!!
        }else{
            details.name!!
        }

         binding.favorite.setOnClickListener {
             viewModel.checkDbForData(id)
             if (!checkDb()) {
                 viewModel.insertInDb(details)
                 binding.favorite.setImageDrawable(
                     ResourcesCompat.getDrawable(
                         resources,
                         R.drawable.heart_24,
                         null
                    )
                 )
                 Toast.makeText(applicationContext, "Inserted $titleText", Toast.LENGTH_LONG).show()
             }else{
                 Toast.makeText(applicationContext, "Already in database", Toast.LENGTH_LONG).show()
             }
         }


        binding.media = details

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

    }

    object DataBinding {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String?) {
            Picasso
                .get()
                .load(Constants.IMAGE_URL + url)
                .fit()
                .into(view)
        }

        @BindingAdapter("genresInfo")
        @JvmStatic
        fun loadGenres(view: MaterialTextView, genresGet: ArrayList<Genres>?) {
            Log.e(TAG, "loadGenres: $genresGet", )
            if (genresGet != null) {
                val genres: ArrayList<Int> = arrayListOf()
                for (data in genresGet) {
                    genres.add(data.id!!)
                }
                view.text = getGenresText(genres)
            }
        }

        @BindingAdapter("movieLength")
        @JvmStatic
        fun loadLength(view: TextView, length: Int?) {

            Log.e(TAG, "loadLength: $length", )
            if (length != null) {
                val appendSeasonsInfo = StringBuilder()

                view.text = appendSeasonsInfo.append(length).append(" ").append("minutes")
            }
        }

        @BindingAdapter("movieYear")
        @JvmStatic
        fun loadYear(view: TextView, year: String?) {

            if (year != null) {
                 view.text = getReleaseYear(year)
            }
        }

        @BindingAdapter("country")
        @JvmStatic
        fun loadCountries(view: TextView, countriesGet: ArrayList<String>?) {
            val countries = StringBuilder()
            if (countriesGet != null) {
                for (detail in countriesGet!!) {
                    countries.append(detail).append(" ")
                }
                view.text = countries
            }
        }

        @BindingAdapter("ratingApply")
        @JvmStatic
        fun loadRating(view: RatingBar, rating: Double?) {

            if (rating != null) {
                view.rating = convertRatings(rating)
            }
        }
    }
}