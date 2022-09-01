package com.company.movieapp.model.subdataclass

import com.google.gson.annotations.SerializedName

data class ProductionCountries (

    @SerializedName("iso_3166_1" ) var iso31661 : String? = null,
    @SerializedName("name"       ) var name     : String? = null

)