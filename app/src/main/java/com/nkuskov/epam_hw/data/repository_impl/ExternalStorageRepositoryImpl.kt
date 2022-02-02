package com.nkuskov.epam_hw.data.repository_impl

import android.content.Context
import com.nkuskov.epam_hw.data.database.external_storage.ExternalStorage
import com.nkuskov.epam_hw.domain.repositries.IExternalStorageRepository

class ExternalStorageRepositoryImpl(context: Context) : IExternalStorageRepository {
    private val externalStorage = ExternalStorage(context)
    @Throws(Exception::class)
    override fun writeData(text: String) {
        externalStorage.writeData(text)
    }

    @Throws(Exception::class)
    override fun readData(): String = externalStorage.readData()
}