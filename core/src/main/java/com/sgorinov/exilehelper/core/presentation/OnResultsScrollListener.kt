package com.sgorinov.exilehelper.core.presentation

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnResultsScrollListener(
    private val totalCount: Int,
    private val onDownloadRequest: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalCurrentItems = recyclerView.layoutManager?.itemCount ?: 0
        if (totalCurrentItems < totalCount) {
            val lastVisiblePosition =
                (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                    ?: 0
            if (totalCurrentItems - 1 <= lastVisiblePosition) {
                onDownloadRequest(totalCurrentItems)
            }
        }
    }
}