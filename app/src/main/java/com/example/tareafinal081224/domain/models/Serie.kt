package com.example.tareafinal081224.domain.models

data class Serie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
)