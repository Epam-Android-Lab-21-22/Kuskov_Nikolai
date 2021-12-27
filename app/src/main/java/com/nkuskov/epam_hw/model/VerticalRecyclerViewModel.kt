package com.nkuskov.epam_hw.model

import kotlin.concurrent.thread
private const val TWO_SECONDS_IN_MILLISECONDS: Long = 2000

class VerticalRecyclerViewModel {

    val items = mutableListOf(
        VerticalItem.TitleItem("График"),
        VerticalItem.CheckboxItem("Прийти на работу", false),
        VerticalItem.CheckboxItem("Выпить кофе", false),
        VerticalItem.CheckboxItem("Пообедать", false),
        VerticalItem.CheckboxItem("Пойти домой", false),
        VerticalItem.TitleItem("Магазины поблизости"),
        VerticalItem.ButtonItem("ОКЕЙ"),
        VerticalItem.ButtonItem("Пятерочка"),
        VerticalItem.ButtonItem("Магнит"),
        VerticalItem.ButtonItem("Перекресток"),
        VerticalItem.ButtonItem("SPAR")
    )

    fun removeItem(item: VerticalItem, listener: VerticalRecyclerViewModelListener) {
        thread {
            Thread.sleep(TWO_SECONDS_IN_MILLISECONDS)
            val index = items.indexOf(item)
            if(items.remove(item)) listener.onItemRemoved(index)
        }
    }
}