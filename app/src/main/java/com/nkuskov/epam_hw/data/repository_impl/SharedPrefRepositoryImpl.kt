package com.nkuskov.epam_hw.data.repository_impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.nkuskov.epam_hw.data.database.shared_pref.SharedPref
import com.nkuskov.epam_hw.domain.repositries.ISharedPrefRepository

class SharedPrefRepositoryImpl(private val sharedPref: SharedPreferences) : ISharedPrefRepository {
    override fun writeData(text: String) {
        sharedPref.edit {
            putString(SharedPref.SHARED_PREF_KEY, text)
        }
    }

    override fun readData(): String =
        sharedPref.getString(SharedPref.SHARED_PREF_KEY, SharedPref.SHARED_PREF_DEFAULT_VALUE)
            ?: SharedPref.SHARED_PREF_DEFAULT_VALUE
}