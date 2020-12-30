package com.example.openmoviedbswiggy

import androidx.recyclerview.widget.GridLayoutManager

internal class ListSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return 1
    }
}
