package com.nkuskov.epam_hw.presentation.view_model.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkuskov.epam_hw.data.repository_impl.DatabaseRepositoryImpl
import com.nkuskov.epam_hw.domain.domain_model.TextMessage
import com.nkuskov.epam_hw.domain.usecases.DatabaseUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewModel(context: Context) : ViewModel() {
    private val repository = DatabaseRepositoryImpl(context)
    private val useCase = DatabaseUseCases(repository)

    private val _textData = MutableLiveData<String?>()
    val textData: LiveData<String?>
        get() = _textData

    fun readData() {
        viewModelScope.launch {
            _textData.value = withContext(Dispatchers.IO) { useCase.getTextFromDatabase() }
        }
    }

    fun writeData(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addTextToDatabase(text)
        }
    }
}