package com.example.mymovies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mymovies.DB.DataBase
import com.example.mymovies.DB.MovieDao
import com.example.mymovies.DB.MovieDatabase
import com.example.mymovies.DB.MovieModel
import com.example.mymovies.Fragments.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VM(application: Application):AndroidViewModel(application) {


    val allMovies: LiveData<List<MovieModel>>
    val repository : MovieRepository

    init {
        val dao = MovieDatabase.getInstance(application).movDao()
        repository = MovieRepository(dao)
        allMovies = repository.allmovie
    }

    fun addmovie(model: MovieModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(model)
        }
    }

    fun deletemovie(model: MovieModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(model)
        }
    }

    fun updatemovie(model: MovieModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(model)
        }
    }

}