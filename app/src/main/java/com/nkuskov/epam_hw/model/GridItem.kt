package com.nkuskov.epam_hw.model

sealed class GridItem{
    data class DefaultItem(val position: Int) : GridItem()
    object Placeholder : GridItem()
}