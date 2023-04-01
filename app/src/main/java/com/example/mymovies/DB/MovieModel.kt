package com.example.mymovies.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MovieTable")
data class MovieModel (
    @PrimaryKey
    val IMDBID: String,
    val Cast: String,
    @SerializedName("Movie Poster")
    val MoviePoster: String,
    val Runtime: String,
    val Title: String,
    val Year: String,
    var favorite: Boolean = false
)

