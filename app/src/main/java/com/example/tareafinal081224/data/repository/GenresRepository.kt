package com.example.tareafinal081224.data.repository

import com.example.tareafinal081224.data.net.ObjectClientApi
import com.example.tareafinal081224.domain.models.Genre
import com.example.tareafinal081224.utils.constants.Constants.API_KEY

class GenresRepository {
    private val apiService = ObjectClientApi.genresListService

    suspend fun getGenres(): List<Genre> {
        return apiService.getGenres(API_KEY).genresList
    }
}