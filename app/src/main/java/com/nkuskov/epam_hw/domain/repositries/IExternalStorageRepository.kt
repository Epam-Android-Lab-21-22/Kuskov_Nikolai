package com.nkuskov.epam_hw.domain.repositries

interface IExternalStorageRepository {
    fun writeData(text: String)
    fun readData() : String
}