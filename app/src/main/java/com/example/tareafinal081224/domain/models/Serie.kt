package com.example.tareafinal081224.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Serie(
    // SerializedName para nombrar las variables diferentes a las que vienen en la API
    @SerializedName("id") val id: Int,
    @SerializedName("original_name") val title: String,
    @SerializedName("overview") val synopsis: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("genre_ids") val genres: Array<Int>
) : Serializable

data class ListadoSeries(
    @SerializedName("results") val listadoSeries: MutableList<Serie>
)
