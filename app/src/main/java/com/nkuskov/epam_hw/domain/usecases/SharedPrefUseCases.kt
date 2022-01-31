package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.repositries.ISharedPrefRepository

class SharedPrefUseCases(private val repository: ISharedPrefRepository) {
    fun addTextToTheSharedPref(text: String) {
        repository.writeData(text)
    }

    fun readTextFromSharedPref() : String = repository.readData()
}