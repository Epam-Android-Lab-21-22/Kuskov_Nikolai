package com.nkuskov.epam_hw.domain.repositries

import com.nkuskov.epam_hw.domain.domain_model.TextMessage

interface ISQLDatabaseRepository {
    @Throws(Exception::class)
    fun writeData(text: String)
    @Throws(Exception::class)
    fun readData() : TextMessage?
}