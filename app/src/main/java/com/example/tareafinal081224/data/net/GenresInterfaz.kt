package com.example.tareafinal081224.data.net

import com.example.tareafinal081224.domain.models.Genres
import com.example.tareafinal081224.utils.constants.Constants.URL_GENRES_ID
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresInterfaz {
    @GET(URL_GENRES_ID)
    suspend fun getGenres(@Query("api_key") key: String): Genres
}