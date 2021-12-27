package com.nkuskov.epam_hw.presenter

import com.nkuskov.epam_hw.model.VerticalItem
import com.nkuskov.epam_hw.model.VerticalRecyclerViewModel
import com.nkuskov.epam_hw.model.VerticalRecyclerViewModelListener
import com.nkuskov.epam_hw.view.VerticalRecyclerView
import kotlin.concurrent.thread

class VerticalRecyclerViewPresenter(private val verticalRecyclerView: VerticalRecyclerView,
                                    private val model: VerticalRecyclerViewModel): VerticalRecyclerViewModelListener {

    fun removeItem(item: VerticalItem) {
        model.removeItem(item, this)
    }

    fun changeCheckedStatus(position: Int, isChecked: Boolean) {
        verticalRecyclerView.changeCheckedStatus(position, isChecked)
    }

    fun getItems(): MutableList<VerticalItem> = model.items

    override fun onItemRemoved(position: Int) {
        verticalRecyclerView.removeItem(position)
    }
}