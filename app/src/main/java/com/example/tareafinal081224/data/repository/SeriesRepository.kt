package com.example.tareafinal081224.data.repository

import com.example.tareafinal081224.data.net.ObjectClientApi
import com.example.tareafinal081224.domain.models.Serie
import com.example.tareafinal081224.utils.constants.Constants.API_KEY

class SeriesRepository {
    private val apiService = ObjectClientApi.seriesListService

    suspend fun getPopulares(): List<Serie> {
        return apiService.getSeriesPopulares(API_KEY).seriesList
    }

    suspend fun getSeriesTopRated(): List<Serie> {
        return apiService.getSeriesTopRated(API_KEY).seriesList
    }

    suspend fun getSeriesAiringToday(): List<Serie> {
        return apiService.getSeriesAiringToday(API_KEY).seriesList
    }

}