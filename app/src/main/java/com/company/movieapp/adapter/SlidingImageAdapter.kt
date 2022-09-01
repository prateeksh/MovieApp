package com.company.movieapp.adapter

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.company.movieapp.SlidingImageFragment
import com.company.movieapp.model.Media

class SlidingImageAdapter(activity: FragmentActivity, val trending: ArrayList<String>) :
    FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return trending.size
    }

    override fun createFragment(position: Int): Fragment {

        Log.e("TAG", "createFragment: ${trending[position]}", )
        return SlidingImageFragment.getInstance(position, trending[position])
    }


}