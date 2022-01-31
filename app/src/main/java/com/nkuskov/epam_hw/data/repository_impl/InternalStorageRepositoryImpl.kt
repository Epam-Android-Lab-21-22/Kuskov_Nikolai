package com.nkuskov.epam_hw.data.repository_impl

import android.content.Context
import com.nkuskov.epam_hw.data.database.internal_storage.InternalStorage
import com.nkuskov.epam_hw.domain.repositries.IInternalStorageRepository

class InternalStorageRepositoryImpl(context: Context) : IInternalStorageRepository {
    private val internalStorage = InternalStorage(context)
    override fun writeData(text: String) {
        internalStorage.writeData(text)
    }

    @Throws(Exception::class)
    override fun readData(): String = internalStorage.readData()
}