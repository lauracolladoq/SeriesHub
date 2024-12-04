package com.example.tareafinal081224.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.databinding.SeriesLayoutBinding
import com.example.tareafinal081224.models.Serie
import com.squareup.picasso.Picasso

class SerieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val binding = SeriesLayoutBinding.bind(v)
    fun render(serie: Serie) {
        binding.tvTitle.text = serie.title
        binding.tvRating.text = serie.rating.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500${serie.backdrop}").into(binding.ivBackdrop)
    }

}
