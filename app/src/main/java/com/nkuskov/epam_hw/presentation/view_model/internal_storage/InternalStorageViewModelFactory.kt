package com.nkuskov.epam_hw.presentation.view_model.internal_storage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InternalStorageViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = InternalStorageViewModel(context) as T
}