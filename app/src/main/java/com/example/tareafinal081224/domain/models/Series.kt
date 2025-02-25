package com.example.tareafinal081224.domain.models

import com.google.gson.annotations.SerializedName

data class Series(
    @SerializedName("results") val seriesList: List<Serie>
)