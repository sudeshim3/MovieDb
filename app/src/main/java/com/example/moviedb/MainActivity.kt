package com.example.moviedb

import AppConstant.BANNER_IMAGE
import AppConstant.IMDB_ID
import AppConstant.MIN_CHAR_FOR_SEARCH
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.text.italic
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.databinding.ActivityMainBinding
import com.example.moviedb.datamodel.MovieDataModel
import com.example.moviedb.extensions.gone
import com.example.moviedb.extensions.visible
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : DaggerAppCompatActivity(), CoroutineScope {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MovieViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var movieRecyclerViewAdapter: MovieRecyclerViewAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    var isGridView = true
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)
        setSearchHintText()

        binding.searchTextField.doAfterTextChanged { updatedText ->
            executeOnSearchTextChanged(updatedText.toString(), savedInstanceState == null)
        }
        observePagedSearchResult()
        observeSearchResult()
        setupMovieAdapter()
        switchToGridView(true)
    }

    private fun setupMovieAdapter() {
        movieRecyclerViewAdapter = MovieRecyclerViewAdapter { movieData, view ->
            navigateToMovieDetailsScreen(movieData, view)
        }
        val movieLoadAdapter = MovieLoadAdapater {
            movieRecyclerViewAdapter.retry()
        }
        gridLayoutManager = binding.rvMovieSearchResult.layoutManager as GridLayoutManager

        binding.rvMovieSearchResult.apply {
            layoutManager = gridLayoutManager
            adapter =
                movieRecyclerViewAdapter.withLoadStateFooter(footer = movieLoadAdapter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.listView -> {
                switchToGridView(true)
                movieRecyclerViewAdapter.setGridView(true)
                movieRecyclerViewAdapter.notifyItemRangeChanged(
                    0,
                    movieRecyclerViewAdapter.itemCount
                )
                invalidateOptionsMenu()
                return true
            }
            R.id.gridView -> {
                switchToGridView(false)
                movieRecyclerViewAdapter.setGridView(false)
                // Used notifyItemRangeChanged for animation
                movieRecyclerViewAdapter.notifyItemRangeChanged(
                    0,
                    movieRecyclerViewAdapter.itemCount
                )
                invalidateOptionsMenu()
                return true
            }
        }
        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.gridView)?.isVisible = isGridView
        menu.findItem(R.id.listView)?.isVisible = !isGridView
        return true
    }

    private fun switchToGridView(showGridView: Boolean) {
        isGridView = showGridView
        if (showGridView) {
            gridLayoutManager.spanCount = 2
            gridLayoutManager.spanSizeLookup = GridSpanSizeLookup(movieRecyclerViewAdapter)
        } else {
            gridLayoutManager.spanCount = 1
            gridLayoutManager.spanSizeLookup = ListSpanSizeLookup()
        }
    }

    private fun showError(error: Throwable) {
        val message: String = when (error.cause) {
            is UnknownHostException -> getString(R.string.unknown_host)
            else -> error.localizedMessage ?: getString(R.string.something_went_wrong)
        }
        if (movieRecyclerViewAdapter.itemCount == 0) {
            binding.errorMessage.text = message
            binding.errorMessage.visible()
        } else {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeSearchResult() {
        viewModel.observeSearchResult().observe(
            this,
            { movieResult ->
                binding.searchLoader.gone()
                when (movieResult) {
                    is MovieResultState.PageResult -> {
                        val totalResults = movieResult.searchResult.totalResults
                        if (totalResults == 0 && movieResult.pageNumber == 1 || binding.searchTextField.text.length <= MIN_CHAR_FOR_SEARCH) {
                            setScreenToEmptyState()
                        } else {
                            setScreenToVisibleState(totalResults)
                        }
                    }
                    is MovieResultState.PageErrorResponse -> {
                        showError(movieResult.throwable)
                    }
                }
            }
        )
    }

    private fun setScreenToVisibleState(searchResultCount: Int) {
        if (searchResultCount != 0) {
            binding.searchResultCount.text = getString(
                R.string.search_result_count,
                searchResultCount
            )
        }
        binding.searchResultCount.visible()
        binding.errorMessage.gone()
    }

    private fun setScreenToEmptyState() {
        binding.searchResultCount.gone()
        binding.searchLoader.gone()
        binding.errorMessage.visible()
        if (binding.searchTextField.text.length <= MIN_CHAR_FOR_SEARCH) {
            setSearchHintText()
        } else {
            binding.errorMessage.text = getString(R.string.no_movies_found)
        }
    }

    private fun observePagedSearchResult() {
        viewModel.pagedSearchResult.observe(
            this,
            {
                if (binding.searchTextField.text.isNotEmpty()) {
                    CoroutineScope(coroutineContext).launch {
                        movieRecyclerViewAdapter.submitData(it)
                    }
                }
            }
        )
    }

    private fun executeOnSearchTextChanged(updatedText: String, shouldRefresh: Boolean) {
        val currentString = updatedText.length
        clearScreen()
        if (currentString > MIN_CHAR_FOR_SEARCH) {
            if (shouldRefresh || updatedText != viewModel.searchString) {
                showLoadingScreen()
                movieRecyclerViewAdapter.refresh()
                viewModel.searchDebounce.invoke(updatedText)
            } else {
                viewModel.collectLatestSearchResult()
            }
        } else {
            binding.searchLoader.gone()
            setSearchHintText()
            binding.errorMessage.visible()
        }
    }

    private fun navigateToMovieDetailsScreen(movieDataModel: MovieDataModel, view: View) {
        var activityOptions: ActivityOptions? = null
        val imageForTransition: View? = view.findViewById(R.id.movie_thumbnail_imageView)
        imageForTransition?.let {
            val posterSharedElement: Pair<View, String> =
                Pair.create(it, getString(R.string.banner_transition))
            activityOptions =
                ActivityOptions.makeSceneTransitionAnimation(this, posterSharedElement)
        }
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(IMDB_ID, movieDataModel.imbdId)
        intent.putExtra(BANNER_IMAGE, movieDataModel.posterImage)
        startActivity(intent, activityOptions?.toBundle())

        overridePendingTransition(0, 0)
    }

    private fun setSearchHintText() {
        binding.errorMessage.text = SpannableStringBuilder(getString(R.string.search_example))
            .italic { append(" Avengers...") }
        binding.errorMessage.visible()
    }

    private fun clearScreen() {
        movieRecyclerViewAdapter.submitData(lifecycle, PagingData.empty())
        binding.searchResultCount.gone()
    }

    private fun showLoadingScreen() {
        binding.searchLoader.visible()
        binding.errorMessage.gone()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
