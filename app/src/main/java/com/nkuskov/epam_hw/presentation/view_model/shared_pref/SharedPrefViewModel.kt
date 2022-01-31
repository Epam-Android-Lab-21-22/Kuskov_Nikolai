package com.nkuskov.epam_hw.presentation.view_model.shared_pref

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkuskov.epam_hw.data.repository_impl.SharedPrefRepositoryImpl
import com.nkuskov.epam_hw.domain.usecases.SharedPrefUseCases

class SharedPrefViewModel(sharedPreferences: SharedPreferences) : ViewModel() {
    private val repository = SharedPrefRepositoryImpl(sharedPreferences)
    private val useCase = SharedPrefUseCases(repository)

    private val _textData = MutableLiveData<String>()
    val textData: LiveData<String>
        get() = _textData

    fun readData() {
        _textData.value = useCase.readTextFromSharedPref()
    }

    fun writeData(text: String) {
        useCase.addTextToTheSharedPref(text)
    }
}