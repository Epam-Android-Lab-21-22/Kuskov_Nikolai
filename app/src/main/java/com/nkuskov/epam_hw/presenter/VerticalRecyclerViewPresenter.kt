package com.nkuskov.epam_hw.presenter

import com.nkuskov.epam_hw.model.VerticalItem
import com.nkuskov.epam_hw.model.VerticalRecyclerViewModel
import com.nkuskov.epam_hw.model.VerticalRecyclerViewModelListener
import com.nkuskov.epam_hw.view.MainActivity
import com.nkuskov.epam_hw.view.VerticalRecyclerView
import kotlin.concurrent.thread

class VerticalRecyclerViewPresenter(private var verticalRecyclerView: VerticalRecyclerView?): VerticalRecyclerViewModelListener {

    fun removeItem(item: VerticalItem) {
        MainActivity.verticalModel.removeItem(item, this)
    }

    fun changeCheckedStatus(position: Int, isChecked: Boolean) {
        verticalRecyclerView?.changeCheckedStatus(position, isChecked)
    }

    fun getItems(): MutableList<VerticalItem> = MainActivity.verticalModel.items

    override fun onItemRemoved(position: Int) {
        verticalRecyclerView?.removeItem(position)
    }

    fun onDestroy(){
        verticalRecyclerView = null
    }
}