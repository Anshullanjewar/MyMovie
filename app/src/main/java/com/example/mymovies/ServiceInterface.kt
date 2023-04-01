package com.example.mymovies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ServiceInterface {

    @Headers("Content-Type:application/json")
    @GET("1.json")
    fun getData():Call<ApiResponse>

    @GET("2.json")
    fun getMoreData():Call<ApiResponse>
}