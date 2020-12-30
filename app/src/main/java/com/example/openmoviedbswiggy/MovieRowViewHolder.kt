package com.example.openmoviedbswiggy

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.openmoviedbswiggy.databinding.MovieRowItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel

class MovieRowViewHolder(private val binding: MovieRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val noImageDrawable =
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_image)

    fun bind(movieDataModel: MovieDataModel) {
        binding.title.text = movieDataModel.title
        /*if (movieDataModel.posterImage == NO_POSTER) {
            binding.movieThumbnailImageView.apply {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                setImageDrawable(noImageDrawable)
            }
        } else {
            Glide.with(binding.root.context)
                .load(movieDataModel.posterImage)
                .centerCrop()
                .into(binding.movieThumbnailImageView)
        }*/
    }
}
