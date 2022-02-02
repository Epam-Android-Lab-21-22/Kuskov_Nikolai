package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.repositries.ISQLDatabaseRepository

class SQLDatabaseUseCases(private val repository: ISQLDatabaseRepository) {
    fun addTextToDatabase(text: String) {
        repository.writeData(text)
    }

    fun getTextFromDatabase() = repository.readData()?.text.toString()
}