package com.company.movieapp.model

import androidx.paging.PagingData

data class FeedItem(

    var type: String,
    var data: ArrayList<PagingData<Media>>
)
