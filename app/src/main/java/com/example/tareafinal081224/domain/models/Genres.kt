package com.example.tareafinal081224.domain.models

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres") val genresList: List<Genre>
)