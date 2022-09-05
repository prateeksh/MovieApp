package com.company.movieapp.model

import com.company.movieapp.model.subdataclass.Cast
import com.google.gson.annotations.SerializedName


data class Person (

    @SerializedName("cast" ) var cast : ArrayList<Cast> = arrayListOf(),
    @SerializedName("id"   ) var id   : Int?            = null
)