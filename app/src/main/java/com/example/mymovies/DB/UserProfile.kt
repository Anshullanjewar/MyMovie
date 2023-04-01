package com.example.mymovies.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var email: String,
    var phone: String,
    var address: String
)
