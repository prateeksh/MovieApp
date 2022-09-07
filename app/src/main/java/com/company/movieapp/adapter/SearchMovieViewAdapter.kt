package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.databinding.MoviesInfoBinding
import com.company.movieapp.model.Media
import com.company.movieapp.model.Person
import com.company.movieapp.model.subdataclass.Cast
import com.company.movieapp.utils.Constants
import com.company.movieapp.utils.convertRatings
import com.company.movieapp.utils.getReleaseYear
import com.squareup.picasso.Picasso


open class SearchMovieViewAdapter(var memberData: Person) : RecyclerView.Adapter<SearchMovieViewAdapter.SearchMovieViewHolder>() {

    var onItemClick: ((Int, String) -> Unit)? = null
    lateinit var binding: MoviesInfoBinding

     inner class SearchMovieViewHolder(var binding: MoviesInfoBinding) : RecyclerView.ViewHolder(binding.root) {

         fun bind(person: Cast){
            binding.cast = person
         }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder{
        Log.e("TAG", "onCreateViewHolder:  adapter", )
        binding = MoviesInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchMovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {

        val list = memberData.cast[position]
        holder.bind(list)


        holder.itemView.setOnClickListener {
            if (list.originalTitle != null) {

                onItemClick?.invoke(
                    list.id!!,
                    Constants.MOVIE
                )
            }else{
                onItemClick?.invoke(
                    list.id!!,
                    Constants.TV
                )
            }
        }
    }

    object DataBinding{

        @BindingAdapter("setImage")
        @JvmStatic
        fun bindImage(view: ImageView, url: String?){
            val uri = Constants.IMAGE_URL
            Picasso
                .get()
                .load(uri + url)
                .fit()
                .into(view)

        }

        @BindingAdapter("setYear")
        @JvmStatic
        fun bindYear(view: TextView, year: String?){
            if (year != null && year != "") {
                view.text = getReleaseYear(year)
            }
        }

    }

    override fun getItemCount(): Int = memberData.cast.size


}