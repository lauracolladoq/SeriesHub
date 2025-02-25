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

}