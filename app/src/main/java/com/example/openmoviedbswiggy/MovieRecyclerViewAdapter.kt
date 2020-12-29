package com.example.openmoviedbswiggy

import AppConstant.LOADER_TYPE
import AppConstant.MOVIE_TYPE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.openmoviedbswiggy.databinding.MovieGridItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieRecyclerViewAdapter :
    PagingDataAdapter<MovieDataModel, MovieViewHolder>(MOVIE_COMPARATOR) {

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

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount)
            return LOADER_TYPE
        else
            return MOVIE_TYPE
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}
