package com.company.movieapp.model.subdataclass

import com.google.gson.annotations.SerializedName


data class Genres (

    @SerializedName(  "id"   ) var id   : Int?    = null,
    @SerializedName( "name" ) var name : String? = null

)