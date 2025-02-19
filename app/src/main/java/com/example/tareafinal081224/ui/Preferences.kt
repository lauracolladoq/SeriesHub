package com.example.tareafinal081224.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Preferences(c: Context) {
    val storage = c.getSharedPreferences("EXPLORER_SETTINGS", MODE_PRIVATE)

    // Popular, top, airing
    fun setSeriesType(seriesType: String) {
        storage.edit().putString("SERIES_TYPE", seriesType).apply()
    }

    fun getSeriesType(): String? {
        return storage.getString("SERIES_TYPE", "popular")
    }

    // Guardar el estado del checkbox
    fun setAdultContent(enabled: Boolean) {
        storage.edit().putBoolean("ADULT_CONTENT", enabled).apply()
    }

    fun getAdultContent(): Boolean {
        return storage.getBoolean("ADULT_CONTENT", false)
    }
}