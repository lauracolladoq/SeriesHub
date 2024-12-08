package com.example.tareafinal081224.providers

import com.example.tareafinal081224.models.ListadoSeries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesInterfaz {
    @GET("3/tv/popular")
    suspend fun getSeriesPopulares(@Query("api_key") key: String): Response<ListadoSeries>

    @GET("3/tv/top_rated")
    suspend fun getSeriesTopRated(@Query("api_key") key: String): Response<ListadoSeries>

    @GET("3/tv/airing_today")
    suspend fun getSeriesAiringToday(@Query("api_key") key: String): Response<ListadoSeries>
}