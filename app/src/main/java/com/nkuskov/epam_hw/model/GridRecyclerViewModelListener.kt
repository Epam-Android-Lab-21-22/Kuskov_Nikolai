package com.nkuskov.epam_hw.model

interface GridRecyclerViewModelListener {
    fun onItemAdded(position: Int)
    fun onItemUpdated(position: Int)
}