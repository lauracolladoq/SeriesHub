package com.example.tareafinal081224.providers

import com.example.tareafinal081224.models.ListadoSeries
import com.example.tareafinal081224.models.Serie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesInterfaz {
    @GET("3/tv/popular")
    suspend fun getSeriesPopulares(@Query("api_key") key: String): Response<ListadoSeries>

    @GET("3/tv/top_rated")
    suspend fun getSeriesTopRated(@Query("api_key") key: String): Response<ListadoSeries>

    @GET("3/tv/airing_today")
    suspend fun getSeriesAiringToday(@Query("api_key") key: String): Response<ListadoSeries>

    // Obtener una serie por su id usando path ya que query es para parametros de la url dinamica
    @GET("3/tv/{id}")
    suspend fun getSerieById(@Path("id") id: Int, @Query("api_key") key: String): Serie
}