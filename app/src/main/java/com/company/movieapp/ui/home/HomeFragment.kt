package com.company.movieapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.MainApplication
import com.company.movieapp.ui.search.SearchableActivity
import com.company.movieapp.adapter.ParentAdapter
import com.company.movieapp.adapter.SlidingImageAdapter
import com.company.movieapp.databinding.FragmentHomeBinding
import com.company.movieapp.model.*
import com.company.movieapp.ui.mediadetails.DetailBottomSheet
import com.company.movieapp.ui.mediadetails.MediaDetailActivity
import com.company.movieapp.utils.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), DataPassing {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory


    private lateinit var viewModel: HomeViewModel
    private lateinit var parentAdapter: ParentAdapter
    private lateinit var recyclerView: RecyclerView
    private var slidingDotsCount = 0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.feedsList

        binding.swipeToRefresh.setOnRefreshListener {
            checkAndInitializeViewModel()
        }
        if(savedInstanceState == null) {
            checkAndInitializeViewModel()
        }

        binding.searchIcon.setOnClickListener {
            val intent = Intent(requireActivity(), SearchableActivity::class.java)
            startActivity(intent)
        }
    }

     private fun checkAndInitializeViewModel(){

        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), "NO Internet", Toast.LENGTH_SHORT).show()
            binding.loader.root.hide()
            binding.swipeToRefresh.isRefreshing = false
        }
        else {


            Toast.makeText(requireContext(), " Internet Available", Toast.LENGTH_SHORT).show()
            binding.swipeToRefresh.isRefreshing = false

            setupViewModel()
           /* recyclerView.hide()
            binding.loader.root.show()*/

        }

    }


    override fun onResume() {
        super.onResume()
    }

    private fun setupViewModel(){


        (requireActivity().application as MainApplication).applicationComponent.inject(this@HomeFragment)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        recyclerView.setHasFixedSize(true)

       fetchDataFromViewModel()

    }


    private fun fetchDataFromViewModel(){
        try {

           /* recyclerView.show()
            binding.loader.root.hide()*/

            val listOfFeedItems = ArrayList<PagingData<Media>>()
            val feedItem = ArrayList<FeedItem>()


            viewModel.getPopularMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

                if (movies != null) {
                    listOfFeedItems.add(movies)
                    feedItem.add(FeedItem("Popular Movies", listOfFeedItems))
                }

            })
            viewModel.getTopRatedMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

                if (movies != null) {
                    listOfFeedItems.add(movies)
                    feedItem.add(FeedItem("Top Rated Movies", listOfFeedItems))
                }

            })
            viewModel.getUpComingMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

                if (movies!= null) {
                    listOfFeedItems.add(movies)
                    feedItem.add(FeedItem("Upcoming Movies", listOfFeedItems))
                }
            })

            viewModel.getPopularTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

                if (tvShows != null) {
                    listOfFeedItems.add(tvShows)
                    feedItem.add(FeedItem("Popular Tv", listOfFeedItems))

                }
            })

            viewModel.getTopRatedTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

                if (tvShows != null) {
                    listOfFeedItems.add(tvShows)
                    feedItem.add(FeedItem("Top Rated Tv", listOfFeedItems))
                }
            })

            viewModel.getOnAirTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

                if (tvShows != null) {
                    listOfFeedItems.add(tvShows)
                    feedItem.add(FeedItem("On Air Tv", listOfFeedItems))
                }

                parentAdapter = ParentAdapter(feedItem, lifecycle, this@HomeFragment)
                recyclerView.adapter = parentAdapter
            })
        }catch (e1: Exception){
            e1.printStackTrace()
        }
    }


    override fun getId(id: Int, title: String) {

        val bottomSheet = DetailBottomSheet()
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("title", title)
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, DetailBottomSheet.TAG)
    }

    override fun getIdOnClick(id: Int, title: String) {
        val intent = Intent(requireActivity(),MediaDetailActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        startActivity(intent)
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }

}