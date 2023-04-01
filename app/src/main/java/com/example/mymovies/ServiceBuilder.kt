package com.example.mymovies

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val baseUrl = "http://task.auditflo.in/"

     private val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()


    fun<T>buildService(service:Class<T>):T{
        return retrofit.create(service)
    }
}