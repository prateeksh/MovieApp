package com.company.movieapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.company.movieapp.MainApplication
import com.company.movieapp.R
import com.company.movieapp.ui.search.SearchableActivity
import com.company.movieapp.adapter.ParentAdapter
import com.company.movieapp.adapter.SearchAdapter
import com.company.movieapp.adapter.SlidingImageAdapter
import com.company.movieapp.databinding.FragmentHomeBinding
import com.company.movieapp.model.*
import com.company.movieapp.ui.details.DetailBottomSheet
import com.company.movieapp.utils.DataPassing
import com.company.movieapp.utils.NetworkUtils
import com.company.movieapp.utils.hide
import com.company.movieapp.utils.show
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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


    private var imagesArray =  ArrayList<String>()
    private var currentPage = 0
    private lateinit var slidingImageDots: Array<ImageView?>
    private var slidingDotsCount = 0

    private val slidingCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            for (i in 0 until slidingDotsCount) {
                slidingImageDots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.non_active_dot
                    )
                )
            }

            slidingImageDots[position]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.active_dot
                )
            )
        }
    }



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

        binding.containerHome.visibility = View.GONE
        binding.loader.root.show()

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        recyclerView.setHasFixedSize(true)


        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), "NO Internet", Toast.LENGTH_SHORT).show()

        } else {
            setupViewPager()
            setupViewModel()
            Toast.makeText(requireContext(), " Internet Available", Toast.LENGTH_SHORT).show()
        }

        binding.searchIcon.setOnClickListener {
            val intent = Intent(requireActivity(), SearchableActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun setupViewPager(){

        viewModel.getTrendingMedia()
        viewModel.trendingMedia.observe(viewLifecycleOwner, Observer { trendingMedia ->
            setUpCustomSlidingViewPager(trendingMedia)
        })
    }


    private fun setUpCustomSlidingViewPager(trendingMedia: ArrayList<Media>){


        slidingDotsCount = trendingMedia.size

        trendingMedia.forEach{
            imagesArray.add(it.posterPath!!)
        }

        val imageAdapter = SlidingImageAdapter(requireActivity(), imagesArray)

        binding.slidingViewPager.apply {
            adapter = imageAdapter
            registerOnPageChangeCallback(slidingCallback)
        }

        slidingImageDots = arrayOfNulls(slidingDotsCount)

        for (i in 0 until slidingDotsCount) {
            slidingImageDots[i] = ImageView(requireContext())
            slidingImageDots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.non_active_dot
                )
            )
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(8, 0, 8, 0)
            binding.sliderDots.addView(slidingImageDots[i], params)
        }

        slidingImageDots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.active_dot
            )
        )

        val handler = Handler()
        val update = Runnable {
            if (currentPage == imagesArray.size) {
                currentPage = 0
            }

            //The second parameter ensures smooth scrolling
            binding.slidingViewPager.setCurrentItem(currentPage++, true)
        }

        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, 3500, 3500)
    }


    private fun setupViewModel(){
        viewModel.getPopularMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

            listOfFeedItems.add(movies)
            feedItem.add(FeedItem("Popular Movies", listOfFeedItems))


        })
        viewModel.getTopRatedMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

            listOfFeedItems.add(movies)
            feedItem.add(FeedItem("Top Rated Movies", listOfFeedItems))



        })
        viewModel.getUpComingMovies()!!.observe(viewLifecycleOwner, Observer { movies ->

            listOfFeedItems.add(movies)
            feedItem.add(FeedItem("Upcoming Movies", listOfFeedItems))
        })

        viewModel.getPopularTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

            listOfFeedItems.add(tvShows)
            feedItem.add(FeedItem("Popular Tv", listOfFeedItems))
        })

        viewModel.getTopRatedTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

            listOfFeedItems.add(tvShows)
            feedItem.add(FeedItem("Top Rated Tv", listOfFeedItems))

        })

        viewModel.getOnAirTv()!!.observe(viewLifecycleOwner, Observer { tvShows ->

            listOfFeedItems.add(tvShows)

            feedItem.add(FeedItem("On Air Tv", listOfFeedItems))

            binding.containerHome.visibility = View.VISIBLE
            binding.loader.root.hide()

            parentAdapter = ParentAdapter(feedItem, lifecycle, this@HomeFragment)
            recyclerView.adapter = parentAdapter
        })
    }


    override fun getId(id: Int, title: String) {

        Log.e(TAG, "getId: $id, $title")
        val bottomSheet = DetailBottomSheet()
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putString("title", title)
        bottomSheet.arguments = bundle
        bottomSheet.show(parentFragmentManager, DetailBottomSheet.TAG)
    }

    override fun onDestroyView() {

        super.onDestroyView()
        feedItem.clear()
        _binding = null
        binding.slidingViewPager.unregisterOnPageChangeCallback(slidingCallback)
    }

}