package com.example.tareafinal081224

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tareafinal081224.adapters.ReviewAdapter
import com.example.tareafinal081224.databinding.ActivityReviewBinding
import com.example.tareafinal081224.models.Review
import com.example.tareafinal081224.models.Serie
import com.example.tareafinal081224.providers.CrudReviews
import com.example.tareafinal081224.providers.ObjectClientApi
import com.example.tareafinal081224.providers.SeriesInterfaz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    var listaReviews = mutableListOf<Review>()
    private lateinit var adapter: ReviewAdapter

    // Spinner
    private lateinit var spinner: ArrayAdapter<String>
    private lateinit var options: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        options = listOf("All", "Positive Rating", "Negative Rating")
        spinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinner

        setListeners()
        setRecycler()
    }

    private fun setListeners() {
        // Al seleccionar un item del spinner creamos un objeto anónimo de tipo AdapterView
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            // Obtener el padre (spinner), la vista (item seleccionado), la posición y el id
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Hilo secundario para filtrar la lista de reseñas
                lifecycleScope.launch(Dispatchers.IO) {
                    val filter = when (position) {
                        1 -> listaReviews.filter { it.rating >= 5 }
                        2 -> listaReviews.filter { it.rating < 5 }
                        else -> listaReviews
                    }

                    // Volver al hilo principal
                    withContext(Dispatchers.Main) {
                        // Actualizar el adapter pasándole la lista filtrada
                        adapter = ReviewAdapter(
                            // Pasamos la lista filtrada a mutableList
                            filter.toMutableList(),
                            { position -> deleteReview(position) },
                            { review -> updateReview(review) }
                        )
                        binding.recyclerView.adapter = adapter
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    private fun getReviews() {
        listaReviews = CrudReviews().read()
        if (listaReviews.size > 0) {
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.INVISIBLE
        }
    }

    private fun setRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        getReviews()

        // adapter = ReviewAdapter(listaReviews)
        adapter = ReviewAdapter(
            listaReviews,
            { position -> deleteReview(position) }, { review -> updateReview(review) })
        binding.recyclerView.adapter = adapter
    }

    private fun updateReview(review: Review) {
        val i = Intent(this, CreateReviewActivity::class.java).apply {
            putExtra("REVIEW", review)
        }
        startActivity(i)
    }

    private fun deleteReview(position: Int) {
        val id = listaReviews[position].id
        // Eliminar de la lista mutable
        listaReviews.removeAt(position)
        // Eliminar de la base de datos
        if (CrudReviews().delete(id)) {
            adapter.notifyItemRemoved(position)
        } else {
            Toast.makeText(this, "ERROR deleting the review", Toast.LENGTH_SHORT).show()
        }
    }

    // Reiniciar la vista al añadir una nueva review
    override fun onRestart() {
        super.onRestart()
        setRecycler()
    }
}