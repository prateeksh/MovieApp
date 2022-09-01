package com.company.movieapp.room

import android.content.Context
import androidx.room.*
import com.company.movieapp.model.Media

@Database(entities = [Media::class], version = 1)
@TypeConverters(Converters::class)
abstract class MediaDatabase: RoomDatabase() {

    abstract fun mediaDao(): MediaDao

   /* companion object{
        private var INSTANCE: MediaDatabase ?= null

        fun getDatabase(context: Context): MediaDatabase{

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MediaDatabase::class.java,
                    "mediaDb"
                ).build()
            }

            return INSTANCE!!
        }
    }*/
}