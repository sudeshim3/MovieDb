package com.example.openmoviedbswiggy

import androidx.recyclerview.widget.GridLayoutManager

class GridSpanSizeLookup(private val movieRecyclerViewAdapter: MovieRecyclerViewAdapter) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return if (position == movieRecyclerViewAdapter.itemCount)
            2
        else
            1
    }
}
