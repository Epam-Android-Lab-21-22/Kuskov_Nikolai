package com.nkuskov.epam_hw.model

import kotlin.concurrent.thread
const val TWO_SECONDS_IN_MILLISECONDS: Long = 2000
class VerticalItemsModel {

    private var _todoItemsList = mutableListOf(
        ToDo("Прийти на работу", false),
        ToDo("Выпить кофе", false),
        ToDo("Пообедать", false),
        ToDo("Пойти домой", false)
    )

    private var _shopItemsList = mutableListOf(
        Shop("ОКЕЙ"),
        Shop("Пятерочка"),
        Shop("Магнит"),
        Shop("Перекресток"),
        Shop("SPAR")
    )

    val todoListTitle = Title("График")
    val shopListTitle = Title("Магазины поблизости")

    val shops: List<Shop>
        get() = _shopItemsList
    val todos: List<ToDo>
        get() = _todoItemsList

    fun removeShop(item: Shop, onSuccess: () -> Unit) {
        thread {
            Thread.sleep(TWO_SECONDS_IN_MILLISECONDS)
            val index = shops.indexOf(item)
            if (index == -1) return@thread
            _shopItemsList.removeAt(index)
            onSuccess()
        }
    }

    fun changeCheckedTodo(item: ToDo, isChecked: Boolean): Boolean {
        val position = _todoItemsList.indexOf(item)
        if (position == -1) return false
        _todoItemsList.removeAt(position)
        val changed = item.copy(isChecked = isChecked)
        _todoItemsList.add(position, changed)
        return true
    }
}