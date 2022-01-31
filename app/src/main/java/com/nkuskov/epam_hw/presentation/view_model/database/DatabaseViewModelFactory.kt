package com.nkuskov.epam_hw.presentation.view_model.database

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkuskov.epam_hw.data.database.room_db.TextDataDB

class DatabaseViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DatabaseViewModel(context) as T
}