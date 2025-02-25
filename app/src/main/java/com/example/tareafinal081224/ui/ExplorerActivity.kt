package com.example.tareafinal081224.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivityExplorerBinding
import com.example.tareafinal081224.domain.models.Serie
import com.example.tareafinal081224.ui.adapters.SerieAdapter
import com.example.tareafinal081224.ui.adapters.SeriesViewModel

class ExplorerActivity : BaseActivity() {
    private lateinit var binding: ActivityExplorerBinding
    //val seriesList = mutableListOf<Serie>()
    //val genresList = mutableListOf<Genre>()

    // MVVM
    private val serieViewModel: SeriesViewModel by viewModels()

    val adapter = SerieAdapter(
        listOf()
    ) { serie -> showDetail(serie) }

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

}

/*
private fun getGenders() {
    lifecycleScope.launch(Dispatchers.IO) {
        val datos = genresListService.getGenres(api)
        Log.d("API_GENRES", "Response: ${datos.body()}")
        val generos = datos.body()?.listadoGenre ?: emptyList()

        // Limpiar la lista de géneros y añadir los nuevos, no hace falta Adapter ya que no se muestra
        genresList.clear()
        genresList.addAll(generos)

        // Modificar la pantalla en el main y no en el IO
        withContext(Dispatchers.Main) {
            if (!datos.isSuccessful) {
                Toast.makeText(
                    this@ExplorerActivity,
                    "Error al cargar los géneros",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
*/

// DETAILED VIEW -------------------------------------------------------------------------------
fun showDetail(serie: Serie) {
    /*
    // Obtiene los nombres de los géneros de la serie seleccionada por su id
    val genreNames = serie.genres.mapNotNull { id ->
        genresList.find { it.id == id }?.name
    }
    val i = Intent(this, DetailActivity::class.java).apply {
        putExtra("serie", serie)
        // Pasar la lista de géneros a la siguiente actividad en forma de ArrayList
        putStringArrayListExtra("genreNames", ArrayList(genreNames))
    }
    startActivity(i)
     */
}