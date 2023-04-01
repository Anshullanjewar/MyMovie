package com.example.mymovies

import com.example.mymovies.DB.MovieModel
import com.google.gson.annotations.SerializedName


data class ApiResponse(

    @SerializedName("Movie List")
    val MovieList: ArrayList<MovieModel> = arrayListOf()
)