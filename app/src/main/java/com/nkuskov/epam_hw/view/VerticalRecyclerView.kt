package com.nkuskov.epam_hw.view

interface VerticalRecyclerView {
    fun removeItem(position: Int)
    fun changeCheckedStatus(position: Int, isChecked: Boolean)
}