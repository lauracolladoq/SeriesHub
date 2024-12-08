package com.example.tareafinal081224.models

import java.io.Serializable

data class Review(
    val id: Int,
    val seriePoster: String,
    val serieTitle: String,
    val rating: Int,
    val comment: String
) : Serializable