package com.nkuskov.epam_hw.data.database.internal_storage

import android.content.Context
import java.io.*
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class InternalStorage(private val context: Context) {

    private val dir = File(context.filesDir, INTERNAL_STORAGE_DIR_NAME)
    init {
        if(!dir.exists()) dir.mkdir()
    }

    @Throws(Exception::class)
    fun writeData(text: String){
        FileOutputStream(File(dir, INTERNAL_STORAGE_FILE_NAME), true).bufferedWriter().use { writer ->
            writer.append("${text}\n")
            writer.close()
        }
    }

    @Throws(Exception::class)
    fun readData() : String {
        File(dir, INTERNAL_STORAGE_FILE_NAME).inputStream().use {
            val isr = InputStreamReader(it)
            val br = BufferedReader(isr)
            return br.readText()
        }
    }

    companion object{
        private const val INTERNAL_STORAGE_FILE_NAME = "text_data.txt"
        private const val INTERNAL_STORAGE_DIR_NAME = "db"
    }
}