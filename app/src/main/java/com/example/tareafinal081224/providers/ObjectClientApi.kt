package com.example.tareafinal081224.providers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ObjectClientApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiClient = retrofit.create(SeriesInterfaz::class.java)
}