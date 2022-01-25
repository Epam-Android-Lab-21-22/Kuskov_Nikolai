package com.nkuskov.epam_hw.presentation.song_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SongInfoViewModelFactory(private val songId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SongInfoViewModel(songId) as T
}