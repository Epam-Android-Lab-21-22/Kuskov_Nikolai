package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.repositries.IExternalStorageRepository

class ExternalStorageUseCases(private val repository: IExternalStorageRepository) {
    fun addTextToTheExternalStorage(text: String) {
        repository.writeData(text)
    }

    fun readTextFromExternalStorage() = repository.readData()
}