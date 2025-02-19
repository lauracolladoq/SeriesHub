package com.example.tareafinal081224.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.databinding.ReviewLayoutBinding
import com.example.tareafinal081224.domain.models.Review
import com.squareup.picasso.Picasso

class ReviewViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val binding = ReviewLayoutBinding.bind(v)

    fun render(r: Review, deleteReview: (Int) -> Unit, updateReview: (Review) -> Unit) {
        // Tengo que obtener de la id de las series los campos necesarios para mostrar
        binding.tvNameReview.text = r.serieTitle
        binding.tvComment.text = r.comment
        binding.tvRatingReview.text = r.rating.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500${r.seriePoster}")
            .into(binding.ivPosterReview)

        binding.btnDelete.setOnClickListener {
            deleteReview(adapterPosition)
        }

        binding.btnUpdate.setOnClickListener {
            updateReview(r)
        }
    }

}

