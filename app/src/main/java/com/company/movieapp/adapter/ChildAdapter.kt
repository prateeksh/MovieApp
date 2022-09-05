package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.model.Media
import com.company.movieapp.utils.Constants
import com.squareup.picasso.Picasso

private const val TAG = "ChildAdapter"

class ChildAdapter : PagingDataAdapter<Media, ChildAdapter.ChildViewHolder>(DiffCallBack) {

    //private var moviesList: List<Movies> = ArrayList()


    var onItemClick: ((Int, String) -> Unit)? = null
    var onClick: ((Int, String) -> Unit)? = null


    var media: Media? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.movies_view_layout,
                parent, false
            )

        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {

        media = getItem(position)!!
        getItemId(position)

        val uri = Constants.IMAGE_URL
        Picasso
            .get()
            .load(uri + media!!.posterPath)
            .fit()
            .into(holder.moviePoster)


        holder.itemView.setOnLongClickListener {

            val clickMedia = peek(position)
            if (clickMedia!!.originalTitle != null) {

                onItemClick?.invoke(
                    clickMedia.id!!,
                    Constants.MOVIE
                )
                Log.e(TAG, "id generated : ${clickMedia.id} ${clickMedia.originalTitle}", )
                Log.e(TAG, "onBindViewHolder: item pos ${peek(position)}", )
            } else {
                onItemClick?.invoke(
                    clickMedia.id!!,
                    Constants.TV
                )

                Log.e(TAG, "id generated : ${clickMedia.id} ${clickMedia.name}", )
                Log.e(TAG, "onBindViewHolder: item pos ${peek(position)}", )
            }

            return@setOnLongClickListener true
        }


        holder.itemView.setOnClickListener {

            val clickMedia = peek(position)
            if (clickMedia!!.originalTitle != null) {

                onClick?.invoke(
                    clickMedia.id!!,
                    Constants.MOVIE
                )
                Log.e(TAG, "id generated : ${clickMedia.id} ${clickMedia.originalTitle}", )
                Log.e(TAG, "onBindViewHolder: item pos ${peek(position)}", )
            } else {
                onClick?.invoke(
                    clickMedia.id!!,
                    Constants.TV
                )

                Log.e(TAG, "id generated : ${clickMedia.id} ${clickMedia.name}", )
                Log.e(TAG, "onBindViewHolder: item pos ${peek(position)}", )
            }
        }

    }


    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            /*itemView.setOnLongClickListener {
                if (media!!.originalTitle != null) {

                    onItemClick?.invoke(
                        media!!.id!!,
                        Constants.MOVIE
                    )
                    Log.e(TAG, "id generated : ${media!!.id} ${media!!.originalTitle}", )
                } else {
                    onItemClick?.invoke(
                        media!!.id!!,
                        Constants.TV
                    )

                    Log.e(TAG, "id generated : ${media!!.id}", )
                }

                return@setOnLongClickListener true
            }*/
        }

        val moviePoster: ImageView = itemView.findViewById(R.id.movie_poster)
    }


    object DiffCallBack : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem == newItem

    }
}