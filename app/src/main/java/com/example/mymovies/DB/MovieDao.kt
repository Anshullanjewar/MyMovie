package com.example.mymovies.DB

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieTable ")
    fun getAllMovies():LiveData<List<MovieModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(model: MovieModel)

    @Delete
    suspend fun deleteMovie(model: MovieModel)

    @Update
    suspend fun update(model: MovieModel)

    @Query("SELECT * FROM MovieTable WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieModel>>

    @Query("UPDATE MovieTable SET favorite = :isFavorite WHERE IMDBID = :movieId")
    suspend fun updateFavoriteStatus(movieId: String, isFavorite: Boolean)


}


