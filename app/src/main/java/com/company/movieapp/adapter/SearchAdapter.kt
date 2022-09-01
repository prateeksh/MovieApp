package com.company.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.databinding.SearchBinding
import com.company.movieapp.model.CommonData
import com.company.movieapp.model.Media
import com.company.movieapp.model.SearchResponse
import com.company.movieapp.utils.Constants
import com.squareup.picasso.Picasso

class SearchAdapter(private var list: List<Media>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            SearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            list[position]
        )
    }

    override fun getItemCount(): Int = list.size

    var onItemClick: ((Int, String) -> Unit)? = null

    inner class ViewHolder(private var item: SearchBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(person: Media) {
            item.name.text = person.title
            val uri = Constants.IMAGE_URL

            Picasso
                .get()
                .load(uri + person.posterPath)
                .into(item.logo)

            item.ratings.text = person.voteAverage.toString()
            item.discription.text = person.overview
        }

        /*init {
            itemView.setOnClickListener {
                if (list.results[adapterPosition].originalTitle != null) {

                    onItemClick?.invoke(
                        list.results[adapterPosition].id!!,
                        Constants.MOVIE
                    )
                }else{
                    onItemClick?.invoke(
                        list.results[adapterPosition].id!!,
                        Constants.TV
                    )
                }
            }
        }*/

    }
}