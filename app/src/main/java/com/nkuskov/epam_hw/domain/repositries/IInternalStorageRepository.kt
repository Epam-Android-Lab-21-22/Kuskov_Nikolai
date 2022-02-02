package com.nkuskov.epam_hw.domain.repositries

interface IInternalStorageRepository {
    @Throws(Exception::class)
    fun writeData(text: String)
    @Throws(Exception::class)
    fun readData() : String
}