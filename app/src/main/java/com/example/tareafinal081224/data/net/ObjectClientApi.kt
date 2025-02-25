package com.example.tareafinal081224.data.net

import com.example.tareafinal081224.utils.constants.Constants.URL_BASE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ObjectClientApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val seriesListService = retrofit.create(SeriesInterfaz::class.java)
    val genresListService = retrofit.create(GenresInterfaz::class.java)
}