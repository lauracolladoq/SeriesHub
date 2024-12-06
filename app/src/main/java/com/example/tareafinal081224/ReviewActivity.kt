package com.example.tareafinal081224

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tareafinal081224.adapters.ReviewAdapter
import com.example.tareafinal081224.databinding.ActivityReviewBinding
import com.example.tareafinal081224.models.Review
import com.example.tareafinal081224.providers.CrudReviews

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    var listaReviews = mutableListOf<Review>()
    private lateinit var adapter: ReviewAdapter

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
        setRecycler()
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
        adapter = ReviewAdapter(listaReviews)
        binding.recyclerView.adapter = adapter
    }

    // Reiniciar la vista al añadir una nueva review
    override fun onRestart() {
        super.onRestart()
        setRecycler()
    }
}