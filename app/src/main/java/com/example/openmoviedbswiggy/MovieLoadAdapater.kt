package com.example.openmoviedbswiggy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openmoviedbswiggy.databinding.LoadStateViewBinding
import com.example.openmoviedbswiggy.extensions.gone
import com.example.openmoviedbswiggy.extensions.visible

class MovieLoadAdapater : LoadStateAdapter<MovieLoadAdapater.LoadStateViewHolder>() {

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
        return LoadStateViewHolder(binding)
    }
}
