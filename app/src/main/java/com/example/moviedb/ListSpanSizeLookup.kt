package com.example.moviedb

import androidx.recyclerview.widget.GridLayoutManager

internal class ListSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return 1
    }
}
