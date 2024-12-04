package com.example.tareafinal081224

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.databinding.ActivityDetailBinding
import com.example.tareafinal081224.models.Genre
import com.example.tareafinal081224.models.Serie
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getDetail()
        setListeners()
    }

    private fun setListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    // Obtiene los detalles de la serie y los muestra en la pantalla
    private fun getDetail() {
        val datos = intent.extras
        val serie: Serie = datos?.getSerializable("serie") as Serie
        val genreNames = intent.getStringArrayListExtra("genreNames") ?: emptyList()
        binding.tvTitleDetail.text = serie.title

        // Si la sinopsis es vacía, muestra un mensaje
        if (serie.synopsis.isEmpty()) {
            binding.tvSynopsis.text = "No synopsis available"
        } else {
            binding.tvSynopsis.text = serie.synopsis
        }
        binding.tvRatingDetail.text = serie.rating.toString()

        // Si la serie es para adultos lo muestra en la pantalla, si no, muestra que es para todas las edades
        if (serie.adult) {
            binding.tvAdults.text = "Adults Only"
        } else {
            binding.tvAdults.text = "For All Audiences"
        }

        // binding.tvGenres.text = serie.genres.joinToString(", ")
        // Imprimir por consola
        Log.d("GENEROS", "Genres: $genreNames")

        // Mostrar los géneros
        binding.tvGenres.text = genreNames.joinToString(", ")

        // Librería Picasso para cargar imágenes
        Picasso.get().load("https://image.tmdb.org/t/p/w500${serie.poster}").into(binding.ivPoster)
    }
}