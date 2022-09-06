package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.model.Media
import com.company.movieapp.utils.Constants
import com.company.movieapp.utils.getReleaseYear
import com.squareup.picasso.Picasso


open class SavedMoviesAdapter(memberData: List<Media>) : RecyclerView.Adapter<SavedMoviesAdapter.DataViewHolder>() {

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

        val moviePoster : ImageView = itemView.findViewById(R.id.movie_img)
        val movieTitle: TextView = itemView.findViewById(R.id.movie_title)
        val movieRating: TextView = itemView.findViewById(R.id.ratings_movie)
        val movieYear: TextView = itemView.findViewById(R.id.year_mov)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder{

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movies_info, parent,
            false)

        return DataViewHolder(view)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {


        val uri = Constants.IMAGE_URL
        val  list = moviesList[position]
        Picasso
            .get()
            .load(uri + list.posterPath)
            .fit()
            .into(holder.moviePoster)

        val titleText: String = if (list.title != null){
            list.title!!
        }else{
            list.name!!

        }

        holder.movieTitle.text = titleText
        holder.movieRating.text = list.voteAverage.toString()

        if (list.releaseDate != null) {
            if (list.releaseDate != null && list.releaseDate != "") {
                holder.movieYear.text = getReleaseYear(list.releaseDate.toString())
            }
        }else{
            holder.movieYear.text = getReleaseYear(list.firstAirDate.toString())
        }
    }

    override fun getItemCount(): Int = moviesList.size


}