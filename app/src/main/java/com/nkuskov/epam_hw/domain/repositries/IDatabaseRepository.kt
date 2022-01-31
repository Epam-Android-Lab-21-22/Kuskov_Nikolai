package com.nkuskov.epam_hw.domain.repositries

import com.nkuskov.epam_hw.domain.domain_model.TextMessage

interface IDatabaseRepository {
    fun writeData(text: String)
    fun readData() : TextMessage?
}