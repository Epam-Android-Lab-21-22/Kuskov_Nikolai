package com.nkuskov.epam_hw.presenter

interface IVerticalRecyclerView {
    fun removeItem(position: Int)
    fun updateItem(position: Int)
    fun changeCheckedStatus(position: Int)
}