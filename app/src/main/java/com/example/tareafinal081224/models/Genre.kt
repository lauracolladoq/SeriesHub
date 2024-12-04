package com.example.tareafinal081224.models

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) {
}

data class ListadoGenre(
    @SerializedName("genres") val listadoGenre: MutableList<Genre>
)