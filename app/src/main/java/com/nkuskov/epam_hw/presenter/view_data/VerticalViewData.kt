package com.nkuskov.epam_hw.presenter.view_data

import com.nkuskov.epam_hw.model.ToDo
import com.nkuskov.epam_hw.model.VerticalItemsModel

class VerticalViewData {
    companion object {
        fun getViewDataFromModel(model: VerticalItemsModel): MutableList<VerticalItem> {
            val result = mutableListOf<VerticalItem>()
            result.add(VerticalItem.TitleItem(model.todoListTitle.title))
            model.todos.forEach {
                result.add(VerticalItem.ToDoItem(it.title, it.isChecked))
            }
            result.add(VerticalItem.TitleItem(model.shopListTitle.title))
            model.shops.forEach {
                result.add(VerticalItem.ShopItem(it.title, false))
            }
            return result
        }

        fun convertTodoToVerticalItem(todo: ToDo): VerticalItem {
            return VerticalItem.ToDoItem(todo.title, todo.isChecked)
        }

    }
}