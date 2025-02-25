package com.example.tareafinal081224.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.data.net.ObjectClientApi.genresListService
import com.example.tareafinal081224.databinding.ActivityExplorerBinding
import com.example.tareafinal081224.domain.models.Genre
import com.example.tareafinal081224.domain.models.Serie
import com.example.tareafinal081224.ui.adapters.SerieAdapter
import com.example.tareafinal081224.ui.adapters.SeriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExplorerActivity : BaseActivity() {
    private lateinit var binding: ActivityExplorerBinding

    val genresList = mutableListOf<Genre>()

    // MVVM
    private val serieViewModel: SeriesViewModel by viewModels()

    val adapter = SerieAdapter(
        listOf()
    ) { serie -> showDetail(serie) }

    // API Key
    private val api = "b18c0a103b3a259545a70d235cde571d"

    // Shared Preferences
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityExplorerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Observers
        serieViewModel.seriesList.observe(this) {
            adapter.list = it
            adapter.notifyDataSetChanged()
            adapter.updateSeries(it)
        }


        preferences = Preferences(this)
        getPreferences()
        setRecycler()
        setListeners()
        getGenders()
    }

    private fun getPreferences() {
        when (preferences.getSeriesType()) {
            "popular" -> binding.btnPopular.isChecked = true
            "top" -> binding.btnTop.isChecked = true
            "airing" -> binding.btnAiring.isChecked = true
        }

        // Recargar ViewModel según el botón seleccionado
        when {
            binding.btnPopular.isChecked -> serieViewModel.getPopulares()
            binding.btnTop.isChecked -> serieViewModel.getTopRated()
            binding.btnAiring.isChecked -> serieViewModel.getAiringToday()

            else -> serieViewModel.getPopulares()
        }
    }

    // Inicializa el RecyclerView
    private fun setRecycler() {
        binding.rvSeries.layoutManager = GridLayoutManager(this, 2)
        binding.rvSeries.adapter = adapter
    }

    private fun setListeners() {
        binding.btnTop.setOnClickListener {
            preferences.setSeriesType("top")
            serieViewModel.getTopRated()
            resetCheckBox()
        }
        binding.btnPopular.setOnClickListener {
            preferences.setSeriesType("popular")
            serieViewModel.getPopulares()
            resetCheckBox()
        }
        binding.btnAiring.setOnClickListener {
            preferences.setSeriesType("airing")
            serieViewModel.getAiringToday()
            resetCheckBox()
        }

        binding.checkBox.setOnClickListener {

            serieViewModel.getSeriesAdultContent(
                binding.checkBox.isChecked,
                preferences.getSeriesType().toString()
            )
        }

        binding.nv.setNavigationItemSelectedListener {
            checkMenuItem(it)
        }
    }

    private fun resetCheckBox() {
        binding.checkBox.isChecked = false
    }

    private fun getGenders() {
        lifecycleScope.launch(Dispatchers.IO) {
            // Obtener los géneros de la API y guardarlos en una lista
            val genres = genresListService.getGenres(api).genresList

            // Limpiar lista y añadir los nuevos
            genresList.clear()
            genresList.addAll(genres)

            // Modificar la pantalla en el main y no en el IO
            withContext(Dispatchers.Main) {
                if (genres.isEmpty()) {
                    Toast.makeText(
                        this@ExplorerActivity,
                        "No se encontraron géneros.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun showDetail(serie: Serie) {
        val genreNames = serie.genre_ids.mapNotNull { id ->
            genresList.find { it.id == id }?.name
        }

        val i = Intent(this, DetailActivity::class.java).apply {
            putExtra("serie", serie)
            // Pasar la lista de géneros a la siguiente actividad en forma de ArrayList
            putStringArrayListExtra("genreNames", ArrayList(genreNames))
        }
        startActivity(i)
    }

}