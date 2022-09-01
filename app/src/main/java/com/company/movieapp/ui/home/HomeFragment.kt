package com.company.movieapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.company.movieapp.adapter.SearchAdapter
import com.company.movieapp.databinding.FragmentHomeBinding
import com.company.movieapp.model.*
import com.company.movieapp.ui.details.DetailBottomSheet
import com.company.movieapp.utils.DataPassing
import com.company.movieapp.utils.NetworkUtils
import javax.inject.Inject

private const val TAG = "HomeFragment"
class HomeFragment : Fragment(), DataPassing {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory


    private lateinit var viewModel: HomeViewModel

    private lateinit var parentAdapter: ParentAdapter

    private lateinit var recyclerView: RecyclerView

    private var listOfFeedItems = ArrayList<PagingData<Media>>()

    private var feedItem = ArrayList<FeedItem>()


    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerView = binding.feedsList

        (requireActivity().application as MainApplication).applicationComponent.inject(this@HomeFragment)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        /*childAdapter = ChildAdapter()
        recyclerView.adapter = childAdapter*/
        recyclerView.setHasFixedSize(true)


        if (!NetworkUtils.isNetworkAvailable(requireContext())){
            Toast.makeText(requireContext(),"NO Internet", Toast.LENGTH_SHORT).show()

        }else{

            viewModel.getPopularMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

                listOfFeedItems.add(movies)
                feedItem.add(FeedItem("Popular Movies", listOfFeedItems))

                Log.e(TAG, "onCreateView: $movies", )

                //childAdapter.submitData(lifecycle, movies)

            })
               viewModel.getTopRatedMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

                   listOfFeedItems.add(movies)
                   feedItem.add(FeedItem("Top Rated Movies", listOfFeedItems))


                  // childAdapter.submitData(lifecycle, movies)

              })
             viewModel.getUpComingMovies()!!.observe(viewLifecycleOwner, Observer { movies ->


                 listOfFeedItems.add(movies)
                 feedItem.add(FeedItem("Upcoming Movies", listOfFeedItems))


                  // childAdapter.submitData(lifecycle, movies)

              })

            viewModel.getPopularTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

                listOfFeedItems.add(tvShows)
                feedItem.add( FeedItem("Popular Tv", listOfFeedItems))


            })

            viewModel.getTopRatedTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

                listOfFeedItems.add(tvShows)
                feedItem.add(FeedItem("Top Rated Tv", listOfFeedItems))

            })

            viewModel.getOnAirTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

                listOfFeedItems.add(tvShows)

                feedItem.add(FeedItem("On Air Tv", listOfFeedItems))

                parentAdapter = ParentAdapter(feedItem, lifecycle, this@HomeFragment)
                recyclerView.adapter = parentAdapter
            })

            Toast.makeText(requireContext()," Internet Available", Toast.LENGTH_SHORT).show()
        }

        //initRecyclerView()
       // performSearch()

        /*binding.searchView.setOnCloseListener {
            binding.searchRecyclerView.visibility = View.INVISIBLE
            return@setOnCloseListener true
        }*/
        binding.searchIcon.setOnClickListener {

            val intent = Intent(requireActivity(), SearchableActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun getId(id: Int, title: String) {

        Log.e(TAG, "getId: $id, $title", )
        val bottomSheet = DetailBottomSheet()
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("title", title)
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, DetailBottomSheet.TAG)
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

}