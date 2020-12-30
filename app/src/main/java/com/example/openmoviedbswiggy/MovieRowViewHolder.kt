package com.example.openmoviedbswiggy

import AppConstant.NO_POSTER
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openmoviedbswiggy.databinding.MovieRowItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieRowViewHolder(private val binding: MovieRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val noImageDrawable =
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_image)

    fun bind(movieDataModel: MovieDataModel) {
        binding.titleTextView.text = movieDataModel.title
        binding.subtitleTextView.text = movieDataModel.year
        if (movieDataModel.posterImage == NO_POSTER) {
            binding.movieImage.apply {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                setImageDrawable(noImageDrawable)
            }
        } else {
            Glide.with(binding.root.context)
                .load(movieDataModel.posterImage)
                .centerCrop()
                .into(binding.movieImage)
        }
    }
}
