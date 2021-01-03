package com.example.openmoviedbswiggy.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.openmoviedbswiggy.R
import com.example.openmoviedbswiggy.data.AppConstant.BANNER_IMAGE
import com.example.openmoviedbswiggy.data.AppConstant.IMDB_ID
import com.example.openmoviedbswiggy.data.AppConstant.NO_POSTER
import com.example.openmoviedbswiggy.databinding.ActivityDetailBinding
import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import com.example.openmoviedbswiggy.extensions.gone
import com.example.openmoviedbswiggy.extensions.visible
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailActivity : DaggerAppCompatActivity(), CoroutineScope {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private var fromDeepLink: Boolean = false

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val noImageDrawable by lazy {
        ContextCompat.getDrawable(this, R.drawable.ic_no_image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val successfulDeeplinkFetch = loadFromDeepLink()
        postponeEnterTransition()
        setBackButtonClickListener()
        observeDetailPageLiveData()

        if (successfulDeeplinkFetch.not()) {
            if (intent.hasExtra(IMDB_ID) && intent.hasExtra(BANNER_IMAGE)) {
                val movieId = intent.getStringExtra(IMDB_ID)!!
                val imageLink = intent.getStringExtra(BANNER_IMAGE)!!
                loadImage(imageLink)
                fetchMovieDetail(movieId)
            } else {
                throw IllegalArgumentException("Missing movie ID")
            }
        }
    }

    private fun observeDetailPageLiveData() {
        detailViewModel.liveData.observe(
            this,
            { movieDetail ->
                when (movieDetail) {
                    is Result.Success -> {
                        showErrorUI(false)
                        binding.plotDetailsGroup.visible()
                        displayDetails(movieDetail.data)
                    }
                    is Result.Error -> {
                        binding.plotDetailsGroup.gone()
                        val message = getErrorMessage(movieDetail.error.cause)
                        showErrorUI(true, message)
                    }
                }
                binding.detailLoaderLottie.gone()
            }
        )
    }

    private fun loadFromDeepLink(): Boolean {
        val intentData = intent.data
        if (intentData != null) {
            val pathSegments = intentData.pathSegments
            if (pathSegments.size > 0) {
                val last = pathSegments.last()
                fromDeepLink = true
                fetchMovieDetail(last)
                return true
            } else
                return false
        }
        return false
    }

    private fun fetchMovieDetail(movieId: String) {
        binding.detailLoaderLottie.visible()
        detailViewModel.fetchMovieDetail(movieId)
    }

    private fun showErrorUI(show: Boolean, message: String? = null) {
        binding.errorMessage.isVisible = show
        binding.retryButton.isVisible = show
        if (message != null) {
            binding.errorMessage.text = message
        } else {
            binding.errorMessage.text = ""
        }
    }

    private fun setBackButtonClickListener() {
        binding.detailsBackButton.setOnClickListener {
            onBackPressed()
        }
        binding.retryButton.setOnClickListener {
            showErrorUI(false)
            fetchMovieDetail(detailViewModel.movieId)
        }
    }

    private fun getErrorMessage(cause: Throwable?): String {
        return when (cause) {
            is UnknownHostException -> getString(R.string.unknown_host)
            else -> cause?.localizedMessage ?: getString(R.string.something_went_wrong)
        }
    }

    private fun displayDetails(movieDetail: MovieDetailDataModel) {
        binding.titleTextView.text = movieDetail.title
        binding.releaseDate.text = getString(R.string.released, movieDetail.releasedDate)
        binding.ratingTextView.text = movieDetail.imdbRating
        binding.plotTextView.text = movieDetail.plot
        binding.boxOfficeRevenueTextView.text =
            getString(R.string.box_office_revenue, movieDetail.boxOfficeRevenue)
        if (fromDeepLink) {
            loadImage(movieDetail.posterImage)
        }
    }

    private fun loadImage(imageUrl: String) {
        if (imageUrl == NO_POSTER) {
            binding.movieThumbnailImageView.setImageDrawable(noImageDrawable)
            startPostponedEnterTransition()
        } else {
            Glide.with(this)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }
                })
                .into(binding.movieThumbnailImageView)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
