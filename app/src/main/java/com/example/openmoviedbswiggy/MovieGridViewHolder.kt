package com.example.openmoviedbswiggy

import AppConstant.NO_POSTER
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openmoviedbswiggy.databinding.MovieGridItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import kotlinx.android.extensions.LayoutContainer

class MovieGridViewHolder(
    private val binding: MovieGridItemBinding,
    val onClick: (String, View) -> Unit
) : RecyclerView.ViewHolder(binding.root), LayoutContainer {
    private val noImageDrawable =
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_image)
    var imdbId: String = ""

    init {
        binding.root.setOnClickListener {
            onClick(imdbId, itemView)
        }
    }

    fun bind(movieDataModel: MovieDataModel) {
        binding.titleTextView.text = movieDataModel.title
        imdbId = movieDataModel.imbdId
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

    override val containerView: View
        get() = itemView
}
