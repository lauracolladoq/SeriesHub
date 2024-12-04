package com.example.tareafinal081224.models

import com.google.gson.annotations.SerializedName

data class Serie(
    // SerializedName para nombrar las variables diferentes a las que vienen en la API
    @SerializedName("original_name") val title: String,
    @SerializedName("original_laguage") val language: String,
    @SerializedName("overview") val synopsis: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("first_air_date") val releaseDate: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("genre_ids") val genres: Array<Int>
)

data class ListadoSeries(
    @SerializedName("results") val listadoSeries: MutableList<Serie>
)
