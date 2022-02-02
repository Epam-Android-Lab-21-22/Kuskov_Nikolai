package com.nkuskov.epam_hw.data.repository_impl

import android.content.Context
import androidx.room.Room
import com.nkuskov.epam_hw.data.data_model.TextData
import com.nkuskov.epam_hw.data.database.room_db.TextDataDB
import com.nkuskov.epam_hw.domain.domain_model.TextMessage
import com.nkuskov.epam_hw.domain.repositries.ISQLDatabaseRepository

class SQLDatabaseRepositoryImpl(context: Context) : ISQLDatabaseRepository {
    private val db = Room.databaseBuilder(
        context, TextDataDB::class.java,
        DB_NAME
    ).build()
    private val dao = db.getDao()

    @Throws(Exception::class)
    override fun writeData(text: String) {
        dao.writeData(TextData().apply {
            id = 0
            data = text
        })
    }
    @Throws(Exception::class)
    override fun readData(): TextMessage? = if(dao.getALL() != null) TextMessage(text = dao.getALL()!!.data) else null

    companion object {
        private const val DB_NAME = "text_data_db"
    }
}