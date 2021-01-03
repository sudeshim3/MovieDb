package com.example.openmoviedbswiggy.ui

import androidx.recyclerview.widget.GridLayoutManager
import com.example.openmoviedbswiggy.ui.adapter.MovieRecyclerViewAdapter

class GridSpanSizeLookup(private val movieRecyclerViewAdapter: MovieRecyclerViewAdapter) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return if (position == movieRecyclerViewAdapter.itemCount)
            2
        else
            1
    }
}
