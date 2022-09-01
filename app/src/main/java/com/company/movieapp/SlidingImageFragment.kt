package com.company.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.company.movieapp.databinding.FragmentSlidingBinding
import com.company.movieapp.utils.Constants
import com.squareup.picasso.Picasso

class SlidingImageFragment: Fragment() {

    private var _binding: FragmentSlidingBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_POSITION = "position"
        const val IMAGE = "IMAGES"
        fun getInstance(position: Int, imageArray: String): Fragment {
            val fragment = SlidingImageFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            bundle.putString(IMAGE, imageArray)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlidingBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)
        val landingImagesArray = requireArguments().getString(IMAGE)

        Picasso
            .get()
            .load(Constants.IMAGE_URL + landingImagesArray)
            .placeholder(R.drawable.placeholder)
            .fit()
            .into(binding.slidingImage)
    }
}