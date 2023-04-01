package com.example.mymovies.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MovieModel::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movDao(): MovieDao

        companion object{
            private var instance: MovieDatabase? = null

            fun getInstance(context: Context): MovieDatabase {
                return instance ?: synchronized(this){
                    instance ?: Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java,"movie_DB").build().also {return it  }
                }
            }
        }

}



//    companion object{
//        private var INSTANCE : MovieDatabase? = null
//
//        fun getInstance(context: Context): MovieDatabase{
//            return instanc
//            if(INSTANCE == null){
//                INSTANCE = Room.databaseBuilder(context, MovieDatabase::class.java,"movieDB").build()
//            }
//            return INSTANCE!!
//        }
//    }
