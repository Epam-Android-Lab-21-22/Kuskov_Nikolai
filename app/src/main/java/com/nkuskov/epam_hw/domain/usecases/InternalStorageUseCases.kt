package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.repositries.IInternalStorageRepository

class InternalStorageUseCases(private val repository: IInternalStorageRepository) {
    @Throws(Exception::class)
    fun addTextToTheInternalStorage(text: String) {
        repository.writeData(text)
    }

    @Throws(Exception::class)
    fun readTextFromInternalStorage() = repository.readData()
}