package com.nkuskov.epam_hw.presenter.view_data

sealed class GridItem{
    data class DefaultItem(val position: Int) : GridItem()
    object Placeholder : GridItem()
}