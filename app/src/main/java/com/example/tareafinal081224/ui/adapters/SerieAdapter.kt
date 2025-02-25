package com.example.tareafinal081224.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.SeriesLayoutBinding
import com.example.tareafinal081224.domain.models.Serie
import com.example.tareafinal081224.utils.constants.Constants.URL_IMG
import com.squareup.picasso.Picasso

class SerieAdapter(
    var list: List<Serie>,
    private var onItemClick: (Serie) -> Unit
    // Crear View Holder dentro del Adapter
) : RecyclerView.Adapter<SerieAdapter.SerieViewHolder>() {
    class SerieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var binding = SeriesLayoutBinding.bind(v)
        fun render(serie: Serie, onItemClick: (Serie) -> Unit) {
            binding.tvTitle.text = serie.original_name
            binding.tvRating.text = serie.vote_average.toString()
            Picasso.get().load("${URL_IMG}${serie.poster_path}").into(binding.ivBackdrop)

            // Efecto
            itemView.setOnClickListener { onItemClick(serie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.series_layout, parent, false)
        return SerieViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        holder.render(list[position], onItemClick)
    }

    fun updateSeries(newList: List<Serie>) {
        list = newList
        notifyDataSetChanged()
    }
}