package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.databinding.MoviesInfoBinding
import com.company.movieapp.databinding.SavedMoviesAdapterBinding
import com.company.movieapp.model.Media
import com.company.movieapp.model.subdataclass.Cast
import com.company.movieapp.utils.Constants
import com.company.movieapp.utils.getReleaseYear
import com.squareup.picasso.Picasso


open class SavedMoviesAdapter(memberData: List<Media>) : RecyclerView.Adapter<SavedMoviesAdapter.DataViewHolder>() {

    private var moviesList: List<Media> = ArrayList()
    private lateinit var binding: SavedMoviesAdapterBinding
    init {

        this.moviesList = memberData
    }

    var onItemClick: ((Int, String) -> Unit)? = null

    inner class DataViewHolder(val binding: SavedMoviesAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        /*init {
            itemView.setOnClickListener {
                if (moviesList[adapterPosition].originalTitle != null) {

                    onItemClick?.invoke(
                        moviesList[adapterPosition].id!!,
                        Constants.MOVIE
                    )
                }else{
                    onItemClick?.invoke(
                        moviesList[adapterPosition].id!!,
                        Constants.TV
                    )
                }
            }
        }*/

        fun bind(person: Media){
            binding.cast = person
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder{

       binding = SavedMoviesAdapterBinding.inflate(LayoutInflater.from(parent.context), parent,
            false)

        return DataViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {


        val uri = Constants.IMAGE_URL
        val  list = moviesList[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int = moviesList.size

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
}