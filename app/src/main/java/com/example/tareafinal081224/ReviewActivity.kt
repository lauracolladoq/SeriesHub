package com.example.tareafinal081224

import android.os.Bundle
import android.view.View
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
        // adapter = ReviewAdapter(listaReviews)
        adapter = ReviewAdapter(listaReviews, { position -> deleteReview(position) })
        binding.recyclerView.adapter = adapter
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