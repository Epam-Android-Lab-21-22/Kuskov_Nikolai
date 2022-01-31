package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.repositries.IDatabaseRepository

class DatabaseUseCases(private val repository: IDatabaseRepository) {
    fun addTextToDatabase(text: String) {
        repository.writeData(text)
    }

    fun getTextFromDatabase() = repository.readData()?.text.toString()
}