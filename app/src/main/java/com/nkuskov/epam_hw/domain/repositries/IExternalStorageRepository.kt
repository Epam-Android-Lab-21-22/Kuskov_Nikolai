package com.nkuskov.epam_hw.domain.repositries

interface IExternalStorageRepository {
    @Throws(Exception::class)
    fun writeData(text: String)
    @Throws(Exception::class)
    fun readData() : String
}