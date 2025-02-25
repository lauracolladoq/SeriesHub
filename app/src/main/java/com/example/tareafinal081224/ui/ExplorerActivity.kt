package com.example.tareafinal081224.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.data.net.ObjectClientApi.seriesListService
import com.example.tareafinal081224.databinding.ActivityExplorerBinding
import com.example.tareafinal081224.domain.models.Genre
import com.example.tareafinal081224.domain.models.Serie
import com.example.tareafinal081224.domain.models.Series
import com.example.tareafinal081224.ui.adapters.SerieAdapter
import com.example.tareafinal081224.ui.adapters.SeriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ExplorerActivity : BaseActivity() {
    private lateinit var binding: ActivityExplorerBinding
    //val seriesList = mutableListOf<Serie>()
    //val genresList = mutableListOf<Genre>()

    private val serieViewModel: SeriesViewModel by viewModels()

    val adapter = SerieAdapter(
        listOf()
    ) { serie -> showDetail(serie) }

    // Shared Preferences
    //private lateinit var preferences: Preferences

    /*
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

        setListeners()

        //preferences = Preferences(this)
        //getPreferences()
        //setRecycler()
    }
    */

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

        serieViewModel.seriesList.observe(this) {
            adapter.list = it
            adapter.notifyDataSetChanged()
            adapter.updateSeries(it)
        }

        setListeners()
    }


    /*
    private fun getPreferences() {
        val adultContent = preferences.getAdultContent()
        binding.checkBox.isChecked = adultContent

        when (preferences.getSeriesType()) {
            "popular" -> binding.btnPopular.isChecked = true
            "top" -> binding.btnTop.isChecked = true
            "airing" -> binding.btnAiring.isChecked = true
        }
    }

    // Inicializa el RecyclerView
    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvSeries.layoutManager = layoutManager
        binding.rvSeries.adapter = adapterSerie
        api = getString(R.string.api_themoviedb)

        setListeners()
        getSeries()
        //getGenders()
    }
    */

    private fun setListeners() {
        binding.btnTop.setOnClickListener {
            //preferences.setSeriesType("top")
            serieViewModel.getTopRated()
        }
        binding.btnPopular.setOnClickListener {
            //preferences.setSeriesType("popular")
            serieViewModel.getPopulares()
        }
        binding.btnAiring.setOnClickListener {
            //preferences.setSeriesType("airing")
            serieViewModel.getAiringToday()
        }

        /*
        binding.checkBox.setOnClickListener {
            //preferences.setAdultContent(binding.checkBox.isChecked)
            getSeries()
        }
        */

        binding.nv.setNavigationItemSelectedListener {
            checkMenuItem(it)
        }

        binding.rvSeries.layoutManager = GridLayoutManager(this, 2)
        binding.rvSeries.adapter = adapter

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

    /*
    // Obtener las series según el botón seleccionado en la pantalla (por defecto las populares)
    private fun getSeries() {
        // 3 Dispatchers, Main, Default, IO
        lifecycleScope.launch(Dispatchers.IO) {
            // Devuelve un objeto Response con la lista de series según el botón seleccionado
            var datos: Response<Series>

            // Modifica también el titulo de la pantalla en el hilo principal (withContext)
            when {
                binding.btnPopular.isChecked -> {
                    datos = seriesListService.getSeriesPopulares(api)
                    withContext(Dispatchers.Main) {
                        binding.tvTitleExplorer.text = getString(R.string.btn_popular)
                    }
                }

                binding.btnTop.isChecked -> {
                    datos = seriesListService.getSeriesTopRated(api)
                    withContext(Dispatchers.Main) {
                        binding.tvTitleExplorer.text = getString(R.string.btn_top)
                    }
                }

                binding.btnAiring.isChecked -> {
                    datos = seriesListService.getSeriesAiringToday(api)
                    withContext(Dispatchers.Main) {
                        binding.tvTitleExplorer.text = getString(R.string.btn_airing)
                    }
                }

                else -> {
                    datos = seriesListService.getSeriesPopulares(api)
                }
            }

            // Datos filtrados por adulto
            if (binding.checkBox.isChecked) {
                // Que aparezcan solo las series para adultos
                datos.body()?.listadoSeries?.removeAll { !it.adult }
            }

            val listaSeries = datos.body()?.listadoSeries ?: emptyList<Serie>().toMutableList()

            // Modificar la pantalla en el hilo principal
            withContext(Dispatchers.Main) {
                adapterSerie.lista = listaSeries
                adapterSerie.notifyDataSetChanged()

                if (!datos.isSuccessful) {
                    Toast.makeText(
                        this@ExplorerActivity,
                        "Error al cargar las series",
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

}