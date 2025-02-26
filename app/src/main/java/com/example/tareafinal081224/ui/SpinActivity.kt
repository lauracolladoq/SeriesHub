package com.example.tareafinal081224.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivitySpinBinding
import com.example.tareafinal081224.ui.adapters.SeriesViewModel
import com.squareup.picasso.Picasso

class SpinActivity : BaseActivity() {
    private lateinit var binding: ActivitySpinBinding

    // MVVM
    private val serieViewModel: SeriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySpinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargo las series populares
        serieViewModel.getPopulares()
        setListeners()
    }

    private fun setListeners() {
        binding.btnSpin.setOnClickListener {
            val serie = serieViewModel.getRandomSerie()
            binding.tvSerieName.text = serie?.original_name
            Picasso.get().load("https://image.tmdb.org/t/p/w500${serie?.poster_path}")
                .into(binding.ivPoster)
        }
    }


}