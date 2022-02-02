package com.nkuskov.epam_hw.presentation.view_model

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkuskov.epam_hw.data.database.shared_pref.SharedPref
import com.nkuskov.epam_hw.data.repository_impl.SQLDatabaseRepositoryImpl
import com.nkuskov.epam_hw.data.repository_impl.ExternalStorageRepositoryImpl
import com.nkuskov.epam_hw.data.repository_impl.InternalStorageRepositoryImpl
import com.nkuskov.epam_hw.data.repository_impl.SharedPrefRepositoryImpl
import com.nkuskov.epam_hw.domain.usecases.SQLDatabaseUseCases
import com.nkuskov.epam_hw.domain.usecases.ExternalStorageUseCases
import com.nkuskov.epam_hw.domain.usecases.InternalStorageUseCases
import com.nkuskov.epam_hw.domain.usecases.SharedPrefUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseFragmentViewModel(context: Context) : ViewModel() {

    private val sharedPrefRepository = SharedPrefRepositoryImpl(
        context.getSharedPreferences(
            SharedPref.SHARED_PREF_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
    )
    private val sharedPrefRUseCase = SharedPrefUseCases(sharedPrefRepository)

    private val internalStorageRepository = InternalStorageRepositoryImpl(context)
    private val internalStorageUseCase = InternalStorageUseCases(internalStorageRepository)

    private val externalStorageRepository = ExternalStorageRepositoryImpl(context)
    private val externalStorageUseCase = ExternalStorageUseCases(externalStorageRepository)

    private val sqlDatabaseRepository = SQLDatabaseRepositoryImpl(context)
    private val sqlDatabaseUseCase = SQLDatabaseUseCases(sqlDatabaseRepository)

    private val _sharedPrefTextData = MutableLiveData<String?>()
    val sharedPrefTextData: LiveData<String?>
        get() = _sharedPrefTextData

    private val _internalStorageTextData = MutableLiveData<String?>()
    val internalStorageTextData: LiveData<String?>
        get() = _internalStorageTextData

    private val _externalStorageTextData = MutableLiveData<String?>()
    val externalStorageTextData: LiveData<String?>
        get() = _externalStorageTextData

    private val _sqlDatabaseTextData = MutableLiveData<String?>()
    val sqlDatabaseTextData: LiveData<String?>
        get() = _sqlDatabaseTextData

    fun readDataFromSharedPref() {
        _sharedPrefTextData.value = sharedPrefRUseCase.readTextFromSharedPref()
    }

    fun writeDataToSharedPref(text: String) {
        sharedPrefRUseCase.addTextToTheSharedPref(text)
    }

    @Throws(Exception::class)
    fun readDataFromInternalStorage() {
        viewModelScope.launch {
            _internalStorageTextData.value =
                withContext(Dispatchers.IO) { internalStorageUseCase.readTextFromInternalStorage() }
        }
    }

    @Throws(Exception::class)
    fun writeDataToInternalStorage(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            internalStorageUseCase.addTextToTheInternalStorage(text)
        }
    }

    @Throws(Exception::class)
    fun readDataFromExternalStorage() {
        viewModelScope.launch {
            _externalStorageTextData.value =
                withContext(Dispatchers.IO) { externalStorageUseCase.readTextFromExternalStorage() }
        }
    }

    @Throws(Exception::class)
    fun writeDataToExternalStorage(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            externalStorageUseCase.addTextToTheExternalStorage(
                text
            )
        }
    }

    @Throws(Exception::class)
    fun readDataFromSQLDatabase() {
        viewModelScope.launch {
            _sqlDatabaseTextData.value =
                withContext(Dispatchers.IO) { sqlDatabaseUseCase.getTextFromDatabase() }
        }
    }

    @Throws(Exception::class)
    fun writeDataToSQLDatabase(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sqlDatabaseUseCase.addTextToDatabase(text)
        }
    }
}