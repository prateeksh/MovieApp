package com.company.movieapp.ui.mediadetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.company.movieapp.MainApplication
import com.company.movieapp.databinding.DetailBottomSheetBinding
import com.company.movieapp.model.Media
import com.company.movieapp.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailBottomSheet : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "Detail View"

    }

    val id: Int?
        get() = arguments?.getInt("id")

    val title: String?
        get() = arguments?.getString("title")

    private var _binding: DetailBottomSheetBinding? = null
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
        _binding = DetailBottomSheetBinding.inflate(inflater, container, false)

        (requireActivity().application as MainApplication).applicationComponent.injectDetails(this)

        //database = MediaDatabase.getDatabase(requireContext())
        setUpViewModel()

        fetchData()
        return binding?.root

    }

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


    private fun showDetailsDialog(details: Media){

        val titleText: String = if (details.title != null){
            details.title!!
        }else{
            details.name!!

        }


        val img = binding!!.mediaImageSheet
        val ratings = binding!!.ratingBar
        val description = binding!!.overview



        Log.e(TAG, "showDetailsDialog: $titleText", )

        binding!!.mediaTitleSheet.text = titleText

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
        for (detail in details.productionCountries) {
            countries.append(detail.iso31661).append(" ")
        }

        //binding!!.country.text = countries
        val genres: ArrayList<Int> = arrayListOf()
        for (data in details.genres){
            genres.add(data.id!!)
        }
        binding!!.geners.text = getGenresText(genres)
        if (details.runTime == null ){
            val appendSeasonsInfo = StringBuilder()
            //binding!!.length.text = "Seasons"
            binding!!.lengthMovie.text = appendSeasonsInfo.append(details.numberOfSeasons.toString()).append(" ").append("Season")
        }else{

            val appendMinutes = StringBuilder()
            binding!!.lengthMovie.text = appendMinutes.append(details.runTime).append(" ").append("minutes")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}