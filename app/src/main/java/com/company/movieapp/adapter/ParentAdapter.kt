package com.company.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.movieapp.R
import com.company.movieapp.model.FeedItem

import com.company.movieapp.utils.DataPassing

private const val TAG = "ParentAdapter"
open class ParentAdapter(private val movies: ArrayList<FeedItem>,
                         val lifecycle: Lifecycle, private val listener: DataPassing) :
    RecyclerView.Adapter<ParentAdapter.DataViewHolder>() {


   private lateinit var childAdapter: ChildAdapter

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieType : TextView = itemView.findViewById(R.id.movie_type)
        var childItem: RecyclerView = itemView.findViewById(R.id.child_items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentAdapter.DataViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.parent_row_items, parent,
            false)
        return DataViewHolder(view)
    }


    override fun getItemCount(): Int = movies.size


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {


        holder.movieType.text = movies[position].type

        val linearLayoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL,false)
        holder.childItem.layoutManager = linearLayoutManager

        childAdapter = ChildAdapter()

        holder.childItem.setHasFixedSize(true)

        holder.childItem.adapter = childAdapter


        val movieData  = movies[position].data[position]

        childAdapter.submitData(lifecycle, movieData)

        Log.e("Parent Adapter", "onBindViewHolder: $movieData" )
        //paging.performPagingOps(holder.childItem)

        childAdapter.onItemClick = { itemId: Int, itemTitle: String ->
            listener.getId(itemId, itemTitle)
        }

    }
}