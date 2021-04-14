package com.example.moviedb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.databinding.LoadStateViewBinding
import com.example.moviedb.extensions.gone
import com.example.moviedb.extensions.visible

class MovieLoadAdapater(val onRetryClicked: () -> Unit) : LoadStateAdapter<MovieLoadAdapater.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setState(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.loadingIcon.visible()
                    binding.retryButton.gone()
                }
                is LoadState.Error -> {
                    binding.loadingIcon.gone()
                    binding.retryButton.visible()
                }
                is LoadState.NotLoading -> {
                    binding.loadingIcon.gone()
                    binding.retryButton.gone()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.setState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.retryButton.setOnClickListener {
            onRetryClicked()
        }
        return LoadStateViewHolder(binding)
    }
}
