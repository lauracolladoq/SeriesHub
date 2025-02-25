package com.example.tareafinal081224.data.net

import com.example.tareafinal081224.domain.models.Series
import com.example.tareafinal081224.utils.constants.Constants.URL_AIRING_TODAY
import com.example.tareafinal081224.utils.constants.Constants.URL_POPULARES
import com.example.tareafinal081224.utils.constants.Constants.URL_TOP_RATED
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesInterfaz {
    @GET(URL_POPULARES)
    suspend fun getSeriesPopulares(@Query("api_key") key: String): Series

    @GET(URL_TOP_RATED)
    suspend fun getSeriesTopRated(@Query("api_key") key: String): Series

    @GET(URL_AIRING_TODAY)
    suspend fun getSeriesAiringToday(@Query("api_key") key: String): Series
}