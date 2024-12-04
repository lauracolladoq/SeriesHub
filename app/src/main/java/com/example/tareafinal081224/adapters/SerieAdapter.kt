package com.example.tareafinal081224.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.R
import com.example.tareafinal081224.models.Serie

class SerieAdapter(
    var lista: MutableList<Serie>,
    private var onItemClick: (Serie) -> Unit
) : RecyclerView.Adapter<SerieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.series_layout, parent, false)
        return SerieViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val serie = lista[position]
        holder.render(serie, onItemClick)
    }
}