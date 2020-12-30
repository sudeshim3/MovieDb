package com.example.openmoviedbswiggy

import AppConstant.NO_POSTER
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openmoviedbswiggy.databinding.MovieRowItemBinding
import com.example.openmoviedbswiggy.datamodel.MovieDataModel
import kotlinx.android.extensions.LayoutContainer

class MovieRowViewHolder(private val binding: MovieRowItemBinding, onClick: (String) -> Unit) :
    RecyclerView.ViewHolder(binding.root), LayoutContainer {
    private val noImageDrawable =
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_no_image)
    private var imdbId: String = ""

    init {
        binding.root.setOnClickListener {
            onClick(imdbId)
        }
    }

    fun bind(movieDataModel: MovieDataModel) {
        imdbId = movieDataModel.imbdId
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

    override val containerView: View
        get() = itemView
}
