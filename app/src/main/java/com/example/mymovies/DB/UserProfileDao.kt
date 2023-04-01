package com.example.mymovies.DB

import androidx.room.*

    @Dao
    interface UserProfileDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(userProfile: UserProfile)

        @Update
        suspend fun update(userProfile: UserProfile)

        @Delete
        suspend fun delete(userProfile: UserProfile)

        @Query("SELECT * FROM user_profile WHERE id = :id")
        suspend fun getById(id: Long): UserProfile?

        @Query("SELECT * FROM user_profile")
        suspend fun getAll(): List<UserProfile>
    }

