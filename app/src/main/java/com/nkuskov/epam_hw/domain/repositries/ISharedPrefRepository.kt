package com.nkuskov.epam_hw.domain.repositries

interface ISharedPrefRepository {
    fun writeData(text: String)
    fun readData() : String
}