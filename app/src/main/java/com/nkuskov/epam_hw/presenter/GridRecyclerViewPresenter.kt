package com.nkuskov.epam_hw.presenter

import com.nkuskov.epam_hw.model.GridItem
import com.nkuskov.epam_hw.model.GridRecyclerViewModel
import com.nkuskov.epam_hw.model.GridRecyclerViewModelListener
import com.nkuskov.epam_hw.view.GridRecyclerView

class GridRecyclerViewPresenter(private val gridRecyclerView: GridRecyclerView,
                                private val model: GridRecyclerViewModel): GridRecyclerViewModelListener {

    fun addNewItem() {
        model.addNewItem(this)
    }

    fun getItems() : MutableList<GridItem> = model.items

    override fun onItemAdded(position: Int) {
        gridRecyclerView.addNewItem(position)
    }

    override fun onItemUpdated(position: Int) {
        gridRecyclerView.updateItem(position)
    }
}