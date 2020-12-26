package com.example.openmoviedbswiggy

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.example.openmoviedbswiggy.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: MovieViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var searchDebounce: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchDebounce = debounce(SEARCH_DEBOUNCE_INTERVAL, Dispatchers.IO + Job()) { searchString ->
            if (searchString.length > MIN_CHAR_FOR_SEARCH) {
                viewModel.fetchMovies(searchString)
            }
        }
        binding.searchView.doAfterTextChanged {
            searchDebounce?.invoke(it.toString())
        }
    }

    private fun debounce(
        delayMs: Long,
        coroutineContext: CoroutineContext,
        f: (String) -> Unit
    ): (String) -> Unit {
        var debounceJob: Job? = null
        return {
            debounceJob?.cancel()
            debounceJob = CoroutineScope(coroutineContext).launch {
                delay(delayMs)
                f(it)
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_INTERVAL = 400L
        private const val MIN_CHAR_FOR_SEARCH = 2
    }
}
