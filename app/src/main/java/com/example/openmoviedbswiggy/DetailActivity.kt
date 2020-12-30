package com.example.openmoviedbswiggy

import AppConstant.IMDB_ID
import android.os.Bundle
import com.example.openmoviedbswiggy.databinding.ActivityDetailBinding
import com.example.openmoviedbswiggy.datamodel.MovieDetailDataModel
import com.example.openmoviedbswiggy.datamodel.Result
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailActivity : DaggerAppCompatActivity(), CoroutineScope {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(IMDB_ID)) {
            val movieId = intent.getStringExtra(IMDB_ID)!!

            detailViewModel.fetchMovieDetail(movieId).observe(
                this,
                { movieDetail ->
                    when (movieDetail) {
                        is Result.Success -> {
                            displayDetails(movieDetail.data)
                        }
                        is Result.Error -> {
                            showError(movieDetail.error.cause)
                        }
                    }
                }
            )
        } else {
            throw IllegalArgumentException("Missing movie ID")
        }
    }

    private fun showError(cause: Throwable?) {
        val message = cause?.localizedMessage ?: getString(R.string.something_went_wrong)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayDetails(movieDetail: MovieDetailDataModel) {
        binding.titleTextView.text = movieDetail.title
        binding.releaseDate.text = movieDetail.year
    }
}
