package com.example.tareafinal081224.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.R
import com.example.tareafinal081224.models.Review

class ReviewAdapter(
    var lista: MutableList<Review>
) : RecyclerView.Adapter<ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.review_layout, parent, false)
        return ReviewViewHolder(v)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.render(lista[position])
    }

}