package com.company.movieapp.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (

            @SerializedName("page"          ) var page         : Int?               = null,
            @SerializedName("results"       ) var results      : ArrayList<Media> = arrayListOf(),
            @SerializedName( "total_pages"   ) var totalPages   : Int?               = null,
            @SerializedName( "total_results" ) var totalResults : Int?               = null

        )