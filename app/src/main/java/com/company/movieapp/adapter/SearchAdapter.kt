package com.company.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.databinding.SearchBinding
import com.company.movieapp.model.Media
import com.company.movieapp.utils.Constants
import com.company.movieapp.utils.hide

class SearchAdapter(private var list: List<Media>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

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
        fun bind(details: Media) {


            val titleText: String = if (details.title != null) {
                details.title!!
            } else {
                details.name!!

            }

            item.name.text = titleText

            itemView.setOnClickListener {
                if(details.mediaType == Constants.MOVIE) {

                    onItemClick?.invoke(
                        details.id!!,
                        Constants.MOVIE
                    )
                } else if (details.mediaType == Constants.TV){
                    onItemClick?.invoke(
                        details.id!!,
                        Constants.TV
                    )
                }
                else{
                    onItemClick?.invoke(
                        details.id!!,
                        Constants.PERSON
                    )
                }
            }
        }
    }
}