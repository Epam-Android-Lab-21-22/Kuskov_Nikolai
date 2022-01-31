package com.nkuskov.epam_hw.presentation.view_model.internal_storage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkuskov.epam_hw.data.repository_impl.InternalStorageRepositoryImpl
import com.nkuskov.epam_hw.domain.usecases.InternalStorageUseCases

class InternalStorageViewModel(context: Context) : ViewModel() {
    private val repository = InternalStorageRepositoryImpl(context)
    private val useCase = InternalStorageUseCases(repository)

    private val _textData = MutableLiveData<String>()
    val textData: LiveData<String>
        get() = _textData

    @Throws(Exception::class)
    fun readData() {
        _textData.value = useCase.readTextFromInternalStorage()
    }

    fun writeData(text: String) {
        useCase.addTextToTheInternalStorage(text)
    }
}