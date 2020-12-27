package com.example.openmoviedbswiggy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.openmoviedbswiggy.databinding.MovieGridItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieRecyclerViewAdapter : ListAdapter<MovieDataModel, MovieViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieDataModel>() {
            override fun areItemsTheSame(
                oldItem: MovieDataModel,
                newItem: MovieDataModel
            ): Boolean =
                oldItem.imbdId == newItem.imbdId

            override fun areContentsTheSame(
                oldItem: MovieDataModel,
                newItem: MovieDataModel
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
