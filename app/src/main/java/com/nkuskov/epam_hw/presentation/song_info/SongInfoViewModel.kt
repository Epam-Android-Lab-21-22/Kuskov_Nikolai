package com.nkuskov.epam_hw.presentation.song_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkuskov.epam_hw.data.repository_impl.SongInfoRepositoryImpl
import com.nkuskov.epam_hw.domain.domain_model.SongInfo
import com.nkuskov.epam_hw.domain.usecases.GetSongInfoByIdUseCase

class SongInfoViewModel(private val id: String) : ViewModel(), ISongInfoViewModel {
    private val repository = SongInfoRepositoryImpl()
    private val useCase = GetSongInfoByIdUseCase(repository)
    override val onSongInfoGot: MutableLiveData<SongInfo> = MutableLiveData(useCase.getSongInfoById(id))
}