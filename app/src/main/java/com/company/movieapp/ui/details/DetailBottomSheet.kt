package com.company.movieapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.MainApplication
import com.company.movieapp.R
import com.company.movieapp.databinding.ActivityDetailsBinding
import com.company.movieapp.model.Media
import com.company.movieapp.paging.TopRatedTvPagingSource
import com.company.movieapp.room.MediaDatabase
import com.company.movieapp.utils.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailBottomSheet : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "Detail View"

    }

    val id: Int?
        get() = arguments?.getInt("id")

    val title: String?
        get() = arguments?.getString("title")

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel


    private var mediaInfo: Media? = null

    val count = MutableLiveData<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityDetailsBinding.inflate(inflater, container, false)

        (requireActivity().application as MainApplication).applicationComponent.injectDetails(this)

        //database = MediaDatabase.getDatabase(requireContext())
        setUpViewModel()

        fetchData()
        return binding?.root

    }

   


 /*   private fun insertToDb(media: Media){
        CoroutineScope(Dispatchers.IO).launch {
            if (database.mediaDao().count(media.id.toString()) == 1) {
                Toast.makeText(requireContext(), "already in db",Toast.LENGTH_LONG).show()
            } else {
                database.mediaDao().insertMedia(media)
            }
        }
        binding!!.favorite.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.heart_24,null))
    }*/

    private fun setUpViewModel(){
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
        viewModel.checkDbForData(id!!)

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
        })



    }


    private fun fetchData(){
        if (title == Constants.TV){
            Log.e(TAG, "fetchData: $title", )
            if (id != null){
                viewModel.fetchTvDetails(id!!)
            }

        }else {
            Log.e(TAG, "fetchData: $title", )
            if (id != null) {
                viewModel.fetchMovieDetails(id!!)
            }
        }
    }


    private fun showLoader(flag: Boolean) {
        if (flag) {
            binding!!.loader.root.show()

        } else {
            binding!!.loader.root.hide()
        }
    }

     private fun checkDb(): Boolean{

         var returnType: Boolean? = null
         viewModel.dataExits.observe(viewLifecycleOwner, Observer {

             returnType = it
             Log.e(TAG, "checkDb: $it", )

         })
       return returnType!!
    }


    private fun showDetailsDialog(details: Media){

        val titleText: String = if (details.title != null){
            details.title!!
        }else{
            details.name!!

        }

        binding!!.favorite.setOnClickListener {
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
        }


        val img = binding!!.imageView
        val ratings = binding!!.ratingBar
        val description = binding!!.overview



        Log.e(TAG, "showDetailsDialog: $titleText", )
        var isShow = true
        var scrollRange = -1
        binding!!.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                binding!!.collapsingToolbar.title = titleText

                isShow = true
            } else if (isShow){
                binding!!.collapsingToolbar.title = " "
                isShow = false
            }
        })

        val genres: ArrayList<Int> = arrayListOf()
        for (data in details.genres){
            genres.add(data.id!!)
        }
        binding!!.geners.text = getGenresText(genres)
        ratings.rating = convertRatings(details.voteAverage!!)
        description.text = details.overview

        Picasso
            .get()
            .load(Constants.DETAIL_IMAGE_URL + details.posterPath)
            .fit()
            .into(img)


        if(details.releaseDate == null){
            binding!!.year.text = getReleaseYear(details.firstAirDate!!)
        }else
            binding!!.year.text = getReleaseYear(details.releaseDate!!)


        val countries = StringBuilder()
        for (details in details.productionCountries) {
            countries.append(details.iso31661).append(" ")
        }

        binding!!.country.text = countries

        if (details.runTime == null ){
            binding!!.length.text = "Seasons"
            binding!!.lengthMovie.text = details.numberOfSeasons.toString()
        }else{

            val appendMinutes = StringBuilder()
            binding!!.lengthMovie.text = appendMinutes.append(details.runTime).append(" ").append("minutes")
        }
    }

    private fun convertRatings(ratings: Double): Float{

        val rate = ratings/2
        return rate.toFloat()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }
}