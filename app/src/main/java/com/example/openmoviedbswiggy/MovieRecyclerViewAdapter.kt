package com.example.openmoviedbswiggy

import AppConstant.LOADER_TYPE
import AppConstant.MOVIE_TYPE_GRID
import AppConstant.MOVIE_TYPE_LIST
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openmoviedbswiggy.databinding.MovieGridItemBinding
import com.example.openmoviedbswiggy.databinding.MovieRowItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieRecyclerViewAdapter(private val onClick: (MovieDataModel, View) -> Unit) :
    PagingDataAdapter<MovieDataModel, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {
    private var isGridView = true

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

    fun setGridView(isGridView: Boolean) {
        this.isGridView = isGridView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MOVIE_TYPE_LIST -> {
                val binding =
                    MovieRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieRowViewHolder(binding, onClick)
            }
            MOVIE_TYPE_GRID -> {
                val binding =
                    MovieGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieGridViewHolder(binding, onClick)
            }
            else -> super.createViewHolder(parent, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount)
            LOADER_TYPE
        else {
            if (isGridView)
                MOVIE_TYPE_GRID
            else
                MOVIE_TYPE_LIST
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            when (holder) {
                is MovieRowViewHolder -> {
                    holder.bind(item)
                }
                is MovieGridViewHolder -> {
                    holder.bind(item)
                }
            }
        }
    }
}
