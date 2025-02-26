package com.example.tareafinal081224.ui.adapters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareafinal081224.data.repository.SeriesRepository
import com.example.tareafinal081224.domain.models.Serie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeriesViewModel : ViewModel() {
    private val repository = SeriesRepository()
    private val _seriesList = MutableLiveData<List<Serie>>()
    val seriesList: MutableLiveData<List<Serie>> get() = _seriesList

    fun getPopulares() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getPopulares()
            _seriesList.postValue(data)
        }
    }

    fun getTopRated() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getSeriesTopRated()
            _seriesList.postValue(data)
        }
    }

    fun getAiringToday() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getSeriesAiringToday()
            _seriesList.postValue(data)
        }
    }

    fun getSeriesAdultContent(isAdultContent: Boolean, seriesType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = when (seriesType) {
                "popular" -> repository.getPopulares()
                "top" -> repository.getSeriesTopRated()
                "airing" -> repository.getSeriesAiringToday()
                else -> repository.getPopulares()
            }

            // Filtrar series con contenido para adultos
            val filteredData = if (isAdultContent) {
                data.filter { it.adult }
            } else {
                data.filter { !it.adult }
            }

            _seriesList.postValue(filteredData)
        }
    }

    fun getRandomSerie(): Serie? {
        // Compruebo que la lista de series no esté vacía
        val seriesList = _seriesList.value ?: emptyList()
        return seriesList.randomOrNull() //
    }
}