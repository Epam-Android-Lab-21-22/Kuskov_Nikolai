package com.nkuskov.epam_hw.data.data_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "textData")
class TextData {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "data")
    var data: String = ""
}