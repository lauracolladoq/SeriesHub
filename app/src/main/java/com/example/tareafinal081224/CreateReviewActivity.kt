package com.example.tareafinal081224

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.databinding.ActivityCreateReviewBinding
import com.example.tareafinal081224.models.Review
import com.example.tareafinal081224.providers.CrudReviews

class CreateReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateReviewBinding
    private var seriePoster = ""
    private var serieTitle = ""
    private var rating = 0
    private var comment = ""
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener poster y título de la serie
        seriePoster = intent.getStringExtra("seriePoster").toString()
        serieTitle = intent.getStringExtra("serieTitle").toString()

        setListeners()
    }

    private fun setListeners() {
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnReset.setOnClickListener {
            binding.seekBar.progress = 0
            binding.etComment.text.clear()
        }

        binding.btnSave.setOnClickListener {
            saveReview()
        }

    }

    private fun saveReview() {
        if (datosCorrectos()) {
            val review = Review(id, seriePoster, serieTitle, rating, comment)
            // Crear review
            CrudReviews().create(review)
            Toast.makeText(this, "Review saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error saving the review", Toast.LENGTH_SHORT).show()
        }
    }

    private fun datosCorrectos(): Boolean {
        rating = binding.seekBar.progress
        comment = binding.etComment.text.toString().trim()
        // Modificación de 150 a 1000 caracteres porque 150 es muy poco
        if (comment.length > 1000) {
            binding.etComment.error = "The comment must be less than 1000 characters"
            return false
        }
        return true
    }
}

