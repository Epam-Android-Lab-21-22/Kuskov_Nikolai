package com.nkuskov.epam_hw.model

sealed class VerticalItem {

    data class TitleItem(
        val title: String
    ) : VerticalItem()

    data class ButtonItem(
        val info: String
    ) : VerticalItem()

    data class CheckboxItem(
        val note: String,
        val isChecked: Boolean
    ) : VerticalItem()
}