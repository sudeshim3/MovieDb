package com.example.openmoviedbswiggy.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openmoviedbswiggy.R
import com.example.openmoviedbswiggy.data.AppConstant.NO_POSTER
import com.example.openmoviedbswiggy.databinding.MovieRowItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import kotlinx.android.extensions.LayoutContainer

class MovieRowViewHolder(private val binding: MovieRowItemBinding, onClick: (MovieDataModel, View) -> Unit) :
    RecyclerView.ViewHolder(binding.root), LayoutContainer {
    private val noImageDrawable =
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_image)
    lateinit var movieDataModel: MovieDataModel

    init {
        binding.root.setOnClickListener {
            onClick(movieDataModel, itemView)
        }
    }

    fun bind(movieDataModel: MovieDataModel) {
        this.movieDataModel = movieDataModel
        binding.titleTextView.text = movieDataModel.title
        binding.subtitleTextView.text = movieDataModel.year
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