package com.nkuskov.epam_hw.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.nkuskov.epam_hw.data.database.shared_pref.SharedPref
import com.nkuskov.epam_hw.databinding.ActivityMainBinding
import com.nkuskov.epam_hw.presentation.view_model.shared_pref.SharedPrefViewModel
import com.nkuskov.epam_hw.presentation.view_model.shared_pref.SharedPrefViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val sharedPrefViewModel: SharedPrefViewModel by viewModels {
        SharedPrefViewModelFactory(
            getSharedPreferences(SharedPref.SHARED_PREF_NAME, MODE_PRIVATE)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}