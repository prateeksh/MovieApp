package com.company.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.movieapp.model.subdataclass.Genres
import com.company.movieapp.model.subdataclass.ProductionCountries
import com.google.gson.annotations.SerializedName

@Entity(tableName = "media")
data class Media(


        @PrimaryKey
        @SerializedName(  "id"                ) var id               : Int?           = null,
        @SerializedName(  "media_type"        ) var mediaType        : String?        = null,
        @SerializedName(  "overview"          ) var overview         : String?        = null,
        @SerializedName(  "poster_path"       ) var posterPath       : String?        = null,
        @SerializedName( "release_date"      ) var releaseDate      : String?        = null,
        @SerializedName( "title"             ) var title            : String?        = null,
        @SerializedName(  "video"             ) var video            : Boolean?       = null,
        @SerializedName( "vote_average"      ) var voteAverage      : Double?        = null,
        @SerializedName( "vote_count"        ) var voteCount        : Int?           = null,
        @SerializedName(  "first_air_date"    ) var firstAirDate     : String?           = null,
        @SerializedName(  "name"              ) var name        : String?           = null,
        @SerializedName( "origin_country"    ) var originCountry    : ArrayList<String> = arrayListOf(),
        @SerializedName(  "original_language" ) var originalLanguage : String?           = null,
        @SerializedName( "original_title"     ) var originalTitle    : String?           = null,

        @SerializedName("production_countries" ) var productionCountries : ArrayList<ProductionCountries> = arrayListOf(),
        @SerializedName("number_of_seasons"    ) var numberOfSeasons     : Int?                           = null,
        @SerializedName("runtime"    ) var runTime     : Int?                           = null,
        @SerializedName(  "genre_ids"         ) var genreIds         : ArrayList<Int> = arrayListOf(),
        @SerializedName("genres"                ) var genres              : ArrayList<Genres>              = arrayListOf(),

        )