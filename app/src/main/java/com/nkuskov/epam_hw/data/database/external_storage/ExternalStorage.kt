package com.nkuskov.epam_hw.data.database.external_storage

import android.content.Context
import com.nkuskov.epam_hw.data.database.internal_storage.InternalStorage
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader

class ExternalStorage(private val context: Context) {
    private val dir = File(context.getExternalFilesDir(null), EXTERNAL_STORAGE_DIR_NAME)
    init {
        if(!dir.exists()) dir.mkdir()
    }

    @Throws(Exception::class)
    fun writeData(text: String){
        FileWriter(File(dir, EXTERNAL_STORAGE_FILE_NAME)).apply {
            append(text)
            flush()
            close()
        }
    }

    @Throws(Exception::class)
    fun readData() : String {
        File(dir, EXTERNAL_STORAGE_FILE_NAME).inputStream().use {
            val isr = InputStreamReader(it)
            val br = BufferedReader(isr)
            return br.readText()
        }
    }

    companion object{
        private const val EXTERNAL_STORAGE_FILE_NAME = "text_data.txt"
        private const val EXTERNAL_STORAGE_DIR_NAME = "db"
    }

}