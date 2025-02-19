package com.example.tareafinal081224.data.repository

import com.example.tareafinal081224.domain.models.ListadoGenre
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresInterfaz {
    @GET("3/genre/tv/list")
    suspend fun getGenres(@Query("api_key") key: String): Response<ListadoGenre>
}