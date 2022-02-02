package com.nkuskov.epam_hw.data.database.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nkuskov.epam_hw.data.data_model.TextData

@Database(entities = [TextData::class], version = 1)
abstract class TextDataDB : RoomDatabase() {
    abstract fun getDao() : TextDataDao
}