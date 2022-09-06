package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.model.Media
import com.company.movieapp.model.Person
import com.company.movieapp.utils.Constants
import com.company.movieapp.utils.convertRatings
import com.company.movieapp.utils.getReleaseYear
import com.squareup.picasso.Picasso


open class SearchMovieViewAdapter(var memberData: Person) : RecyclerView.Adapter<SearchMovieViewAdapter.SearchMovieViewHolder>() {

    var onItemClick: ((Int, String) -> Unit)? = null

    class SearchMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        val title : TextView = itemView.findViewById(R.id.movie_title)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar)
        val year : TextView = itemView.findViewById(R.id.year_mov)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder{
        Log.e("TAG", "onCreateViewHolder:  adapter", )
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movies_info, parent,
            false)

        return SearchMovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {

        Log.e("TAG", "onBindViewHolder:  adapter", )
        val list = memberData.cast[position]
        val uri = Constants.IMAGE_URL
        Picasso
            .get()
            .load(uri + list.posterPath)
            .fit()
            .into(holder.moviePoster)

        holder.title.text = list.title
        holder.rating.rating = convertRatings(list.voteAverage!!)
       /* if (list.releaseDate != null) {
            Log.e("TAG", "onBindViewHolder: ${list.releaseDate}", )
            holder.year.text = getReleaseYear(list.releaseDate.toString())
        }*/
    }

    override fun getItemCount(): Int = memberData.cast.size


}