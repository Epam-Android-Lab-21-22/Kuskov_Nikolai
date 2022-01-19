package com.nkuskov.epam_hw.presenter

interface IGridRecyclerView {
    fun updateItem(position: Int)
    fun addNewItem(position: Int)
}