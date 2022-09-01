package com.company.movieapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.company.movieapp.model.Media

@Dao
interface MediaDao {

    @Insert
    fun insertMedia(matches: Media)

    @Query(value = "SELECT * FROM media")
    fun getMedia(): MutableList<Media>

    @Query(value = "DELETE FROM media WHERE id = :id")
    fun deleteMedia(id: String)

    @Query("SELECT EXISTS(SELECT * FROM media WHERE id = :id)")
    fun checkExistence(id: String): Boolean

}