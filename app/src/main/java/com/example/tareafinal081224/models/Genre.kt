package com.example.tareafinal081224.models

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name") val name: String,
)

data class ListadoGenre(
    @SerializedName("results") val listadoGenre: Array<Genre>
)