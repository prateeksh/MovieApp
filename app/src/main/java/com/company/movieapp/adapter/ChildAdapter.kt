package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.databinding.MoviesViewLayoutBinding
import com.company.movieapp.databinding.SearchBinding
import com.company.movieapp.model.Media
import com.company.movieapp.utils.Constants
import com.squareup.picasso.Picasso

private const val TAG = "ChildAdapter"

class ChildAdapter : PagingDataAdapter<Media, ChildAdapter.ChildViewHolder>(DiffCallBack) {

    //private var moviesList: List<Movies> = ArrayList()

    private lateinit var binding: MoviesViewLayoutBinding

    var onItemClick: ((Int, String) -> Unit)? = null
    var onClick: ((Int, String) -> Unit)? = null


    var media: Media? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {

       binding = MoviesViewLayoutBinding.inflate(LayoutInflater.from(parent.context),
       parent, false)

        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {

        media = getItem(position)!!
        getItemId(position)
        holder.bind(media!!)

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

    object BindAdapter {

        @BindingAdapter("setImage")
        @JvmStatic
        fun bindImage(view: ImageView, img: String?){
            val uri = Constants.IMAGE_URL
            Picasso
                .get()
                .load(uri + img)
                .fit()
                .into(view)

        }
    }

    class ChildViewHolder(val binding: MoviesViewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (media: Media){
            binding.media = media
        }
    }


    object DiffCallBack : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem == newItem

    }
}