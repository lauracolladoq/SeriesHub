package com.example.tareafinal081224

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tareafinal081224.adapters.SerieAdapter
import com.example.tareafinal081224.databinding.ActivityExplorerBinding
import com.example.tareafinal081224.models.Serie
import com.example.tareafinal081224.providers.ObjectClientApi.apiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExplorerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExplorerBinding
    val lista = mutableListOf<Serie>()
    val adapter = SerieAdapter(lista)
    var api = ""

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

        setRecycler()
    }

    // Inicializa el RecyclerView
    private fun setRecycler() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvSeries.layoutManager = layoutManager
        binding.rvSeries.adapter = adapter
        api = getString(R.string.api_themoviedb)
        getSeries()
    }

    private fun getSeries() {
        // 3 Dispatchers, Main, Default, IO
        lifecycleScope.launch(Dispatchers.IO) {
            val datos = apiClient.getSeriesPopulares(api)
            val listaSeries = datos.body()?.listadoSeries ?: emptyList<Serie>().toMutableList()

            // Modificar la pantalla en el main y no en el IO
            withContext(Dispatchers.Main) {
                adapter.lista = listaSeries
                adapter.notifyDataSetChanged()
            }
            if (!datos.isSuccessful) {
                // @ExplorerActivity ya que estamos en el hilo
                Toast.makeText(
                    this@ExplorerActivity,
                    "Error al cargar las series",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // NAVIGATION MENU -----------------------------------------------------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_exit -> {
                // Finalizar todas las actividades de la app
                finishAffinity()
            }

            R.id.item_profile -> {
                cargarActivity(MainActivity::class.java)
            }

            R.id.item_explorer -> {
                cargarActivity(ExplorerActivity::class.java)
            }

            R.id.item_favorites -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cargarActivity(java: Class<*>) {
        val intent = Intent(this, java)
        startActivity(intent)
    }
}
