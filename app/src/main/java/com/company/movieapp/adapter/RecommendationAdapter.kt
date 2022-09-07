package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.model.Media
import com.company.movieapp.utils.Constants
import com.squareup.picasso.Picasso


open class RecommendationAdapter(memberData: List<Media>) : RecyclerView.Adapter<RecommendationAdapter.DataViewHolder>() {

    private var moviesList: List<Media> = ArrayList()

    init {

        this.moviesList = memberData
    }

    var onItemClick: ((Int, String) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

       // val moviePoster : ImageView = itemView.findViewById(R.id.movie_poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder{

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movies_view_layout, parent,
            false)
        Log.e("TAG", "onCreateViewHolder: Recomend adapter", )
        return DataViewHolder(view)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        Log.e("TAG", "onBindViewHolder: Recomend adapter", )
        val uri = Constants.IMAGE_URL
       /* Picasso
            .get()
            .load(uri + moviesList[position].posterPath)
            .fit()
            .into(holder.moviePoster)*/

    }

    override fun getItemCount(): Int = moviesList.size


}