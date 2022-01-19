package com.nkuskov.epam_hw.presenter

import com.nkuskov.epam_hw.model.GridItemsModel
import com.nkuskov.epam_hw.presenter.view_data.GridItem

class GridRecyclerViewPresenter {

    val model = GridItemsModel()
    var gridRecyclerView: IGridRecyclerView? = null

    private val _gridItems = mutableListOf<GridItem>()

    val items: List<GridItem>
        get() = _gridItems

    fun addNewItem() {
        val position = items.size
        _gridItems.add(GridItem.Placeholder)
        gridRecyclerView?.addNewItem(position)
        model.addNewStar {
            _gridItems[position] = GridItem.DefaultItem(position)
            gridRecyclerView?.updateItem(position)
        }
    }

    fun onDestroy(){
        gridRecyclerView = null
    }
}