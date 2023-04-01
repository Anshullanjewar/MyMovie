package com.example.mymovies.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovies.DB.MovieDao
import com.example.mymovies.DB.MovieModel
import com.example.mymovies.ServiceInterface

class MovieRepository(private val movieDao: MovieDao) {

   val allmovie: LiveData<List<MovieModel>> = movieDao.getAllMovies()

    suspend fun insert(model: MovieModel){
        movieDao.addMovie(model)
    }

    suspend fun  delete(model: MovieModel){
        movieDao.deleteMovie(model)
    }

    suspend fun  update(model: MovieModel){
        movieDao.update(model)
    }


}