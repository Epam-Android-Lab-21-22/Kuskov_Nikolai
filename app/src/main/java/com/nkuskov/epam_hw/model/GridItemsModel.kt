package com.nkuskov.epam_hw.model

import kotlin.concurrent.thread
private const val ONE_SECOND_IN_MILLISECONDS: Long = 1000

class GridItemsModel {

    private var _stars = mutableListOf<Star>()
    val stars: List<Star>
        get() = _stars

    fun addNewStar(onAdded: () -> Unit){
        thread {
            Thread.sleep(ONE_SECOND_IN_MILLISECONDS)
            _stars.add(Star(_stars.size))
            onAdded()
        }
    }
}