package com.nkuskov.epam_hw.presentation.view_model.external_storage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkuskov.epam_hw.data.repository_impl.ExternalStorageRepositoryImpl
import com.nkuskov.epam_hw.domain.usecases.ExternalStorageUseCases

class ExternalStorageViewModel(context: Context) : ViewModel() {
    private val repository = ExternalStorageRepositoryImpl(context)
    private val useCase = ExternalStorageUseCases(repository)

    private val _textData = MutableLiveData<String>()
    val textData: LiveData<String>
        get() = _textData

    fun readData() {
        _textData.value = useCase.readTextFromExternalStorage()
    }

    fun writeData(text: String) {
        useCase.addTextToTheExternalStorage(text)
    }
}