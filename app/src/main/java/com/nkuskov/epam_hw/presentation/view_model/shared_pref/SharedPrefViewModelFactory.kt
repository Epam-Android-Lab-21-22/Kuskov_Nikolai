package com.nkuskov.epam_hw.presentation.view_model.shared_pref

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SharedPrefViewModelFactory(private val sharedPref: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SharedPrefViewModel(sharedPreferences = sharedPref) as T
}