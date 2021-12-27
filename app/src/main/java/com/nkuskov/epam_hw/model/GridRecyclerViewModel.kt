package com.nkuskov.epam_hw.model

import kotlin.concurrent.thread
private const val ONE_SECOND_IN_MILLISECONDS: Long = 1000

class GridRecyclerViewModel {

    val items = mutableListOf<GridItem>()

    fun addNewItem(listener: GridRecyclerViewModelListener){
        thread {
            val newItem = GridItem.Placeholder
            items.add(newItem)
            listener.onItemAdded(items.size)
            Thread.sleep(ONE_SECOND_IN_MILLISECONDS)
            val index = items.indexOf(newItem)
            val updatedItem = GridItem.DefaultItem(index)
            items[index] = updatedItem
            listener.onItemUpdated(index)
        }
    }
}