package com.nkuskov.epam_hw.data.database.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nkuskov.epam_hw.data.data_model.TextData
import com.nkuskov.epam_hw.domain.domain_model.TextMessage

@Dao
interface TextDataDao {
    @Query("SELECT * FROM textData ORDER BY id DESC LIMIT 1")
    fun getALL(): TextData?

    @Insert
    fun writeData(textData: TextData)
}