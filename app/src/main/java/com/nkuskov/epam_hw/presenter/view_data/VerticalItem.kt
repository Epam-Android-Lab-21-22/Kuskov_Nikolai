package com.nkuskov.epam_hw.presenter.view_data

sealed class VerticalItem {

    data class TitleItem(
        val title: String
    ) : VerticalItem()

    data class ShopItem(
        val info: String,
        val loading: Boolean
    ) : VerticalItem()

    data class ToDoItem(
        val note: String,
        val isChecked: Boolean
    ) : VerticalItem()
}