package com.example.openmoviedbswiggy

import AppConstant.LOADER_TYPE
import AppConstant.MIN_CHAR_FOR_SEARCH
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.WindowManager
import androidx.core.text.italic
import androidx.core.widget.doAfterTextChanged
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.openmoviedbswiggy.databinding.ActivityMainBinding
import com.example.openmoviedbswiggy.extensions.gone
import com.example.openmoviedbswiggy.extensions.visible
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: MovieViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var movieRecyclerViewAdapter: MovieRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        setSearchHintText()

        binding.searchView.doAfterTextChanged { updatedText ->
            executeOnSearchTextChanged(updatedText.toString())
        }
        observePagedSearchResult()
        observeSearchResult()

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieRecyclerViewAdapter.getItemViewType(position)
                return if (viewType == LOADER_TYPE) 2 else 1
            }
        }
        movieRecyclerViewAdapter = MovieRecyclerViewAdapter()
        binding.rvMovieSearchResult.apply {
            layoutManager = gridLayoutManager
            adapter =
                movieRecyclerViewAdapter.withLoadStateFooter(footer = MovieLoadAdapater())
        }
    }

    private fun observeSearchResult() {
        viewModel.observeSearchResult().observe(
            this,
            {
                binding.searchLoader.gone()
                val (pageNumber, searchResult) = it
                if (searchResult.totalResults == 0 && pageNumber == 1 || binding.searchView.text.length <= MIN_CHAR_FOR_SEARCH) {
                    setScreenToEmptyState()
                } else {
                    setScreenToVisibleState(searchResult.totalResults)
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
        if (binding.searchView.text.length <= MIN_CHAR_FOR_SEARCH) {
            setSearchHintText()
        } else {
            binding.errorMessage.text = getString(R.string.no_movies_found)
        }
    }

    private fun observePagedSearchResult() {
        viewModel.pagedSearchResult.observe(
            this,
            {
                CoroutineScope(Dispatchers.IO).launch {
                    movieRecyclerViewAdapter.submitData(it)
                }
            }
        )
    }

    private fun executeOnSearchTextChanged(updatedText: String) {
        val currentString = updatedText.length
        clearScreen()
        if (currentString > MIN_CHAR_FOR_SEARCH) {
            showLoadingScreen()
            movieRecyclerViewAdapter.refresh()
            viewModel.searchDebounce.invoke(updatedText)
        } else {
            binding.searchLoader.gone()
            setSearchHintText()
            binding.errorMessage.visible()
        }
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
}
