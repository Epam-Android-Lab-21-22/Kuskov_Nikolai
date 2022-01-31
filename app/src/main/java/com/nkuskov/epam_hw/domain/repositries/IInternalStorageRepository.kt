package com.nkuskov.epam_hw.domain.repositries

interface IInternalStorageRepository {
    fun writeData(text: String)
    @Throws(Exception::class)
    fun readData() : String
}