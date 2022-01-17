package com.nkuskov.epam_hw.presenter

import com.nkuskov.epam_hw.model.GridItem
import com.nkuskov.epam_hw.model.GridRecyclerViewModel
import com.nkuskov.epam_hw.model.GridRecyclerViewModelListener
import com.nkuskov.epam_hw.view.GridRecyclerView
import com.nkuskov.epam_hw.view.MainActivity

class GridRecyclerViewPresenter(private var gridRecyclerView: GridRecyclerView?): GridRecyclerViewModelListener {

    fun addNewItem() {
        MainActivity.gridModel.addNewItem(this)
    }

    fun getItems() : MutableList<GridItem> = MainActivity.gridModel.items

    override fun onItemAdded(position: Int) {
        gridRecyclerView?.addNewItem(position)
    }

    override fun onItemUpdated(position: Int) {
        gridRecyclerView?.updateItem(position)
    }

    fun onDestroy(){
        gridRecyclerView = null
    }
}