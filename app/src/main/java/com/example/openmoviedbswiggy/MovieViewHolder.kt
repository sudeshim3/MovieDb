package com.example.openmoviedbswiggy

import androidx.recyclerview.widget.RecyclerView
import com.example.openmoviedbswiggy.databinding.MovieGridItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieViewHolder(private val binding: MovieGridItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movieDataModel: MovieDataModel) {
        binding.titleTextView.text = movieDataModel.title
    }
}
