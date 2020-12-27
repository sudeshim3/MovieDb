package com.example.openmoviedbswiggy

import AppConstant.NO_POSTER
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openmoviedbswiggy.databinding.MovieGridItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieViewHolder(private val binding: MovieGridItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val noImageDrawable =
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_image)

    fun bind(movieDataModel: MovieDataModel) {
        binding.titleTextView.text = movieDataModel.title
        if (movieDataModel.posterImage == NO_POSTER) {
            binding.movieThumbnailImageView.apply {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                setImageDrawable(noImageDrawable)
            }
        } else {
            Glide.with(binding.root.context)
                .load(movieDataModel.posterImage)
                .centerCrop()
                .into(binding.movieThumbnailImageView)
        }
    }
}
