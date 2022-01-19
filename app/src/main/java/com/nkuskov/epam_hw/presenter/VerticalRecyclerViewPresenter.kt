package com.nkuskov.epam_hw.presenter

import com.nkuskov.epam_hw.model.Shop
import com.nkuskov.epam_hw.model.ToDo
import com.nkuskov.epam_hw.model.VerticalItemsModel
import com.nkuskov.epam_hw.presenter.view_data.VerticalItem
import com.nkuskov.epam_hw.presenter.view_data.VerticalViewData

class VerticalRecyclerViewPresenter{

    val model = VerticalItemsModel()
    var verticalRecyclerView: IVerticalRecyclerView? = null

    private val _items = VerticalViewData.getViewDataFromModel(model)

    val items get() = _items

    fun removeItem(position: Int) {
        val shopToRemove = items[position] as? VerticalItem.ShopItem ?: return
        val shop = Shop(shopToRemove.info)
        val onLoadingItem = VerticalItem.ShopItem(shopToRemove.info, true)
        items[position] = onLoadingItem
        verticalRecyclerView?.updateItem(position)
        model.removeShop(shop) {
            val removingIndex = items.indexOf(onLoadingItem)
            _items.removeAt(removingIndex)
            verticalRecyclerView?.removeItem(removingIndex)
        }
    }

    fun changeCheckedStatus(position: Int, isChecked: Boolean) {
        val todoItem = items[position] as? VerticalItem.ToDoItem ?: return
        if (model.changeCheckedTodo(ToDo(todoItem.note, todoItem.isChecked), isChecked)) {
            _items[position] =
                VerticalViewData.convertTodoToVerticalItem(ToDo(todoItem.note, isChecked))
            verticalRecyclerView?.changeCheckedStatus(position)
        }
    }

    fun onDestroy(){
        verticalRecyclerView = null
    }
}