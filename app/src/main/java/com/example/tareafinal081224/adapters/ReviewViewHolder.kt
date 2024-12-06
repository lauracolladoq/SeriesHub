package com.example.tareafinal081224.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.databinding.ReviewLayoutBinding
import com.example.tareafinal081224.models.Review
import com.squareup.picasso.Picasso

class ReviewViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val binding = ReviewLayoutBinding.bind(v)
    fun render(r: Review) {
        // Tengo que obtener de la id de las series los campos necesarios para mostrar
        binding.tvNameReview.text = r.serieId.toString()
        binding.tvComment.text = r.comment
        binding.tvRatingReview.text = r.rating.toString()
        // Picasso.get().load(r.serieId).into(binding.ivPosterReview)
    }

}
