package com.nkuskov.epam_hw.presentation.song_preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nkuskov.epam_hw.data.repository_impl.SongsPreviewRepository
import com.nkuskov.epam_hw.domain.domain_model.SongPreview
import com.nkuskov.epam_hw.domain.usecases.GetSongPreviewsUseCase

class SongPreviewsViewModel : ViewModel(), ISongPreviewsViewModel {

    private val songsPreviewRepository = SongsPreviewRepository()
    private val getSongsPreviewUseCase = GetSongPreviewsUseCase(songsPreviewRepository)
    override val songsPreviewUpdated: MutableLiveData<Set<SongPreview>> =
        MutableLiveData(getSongsPreviewUseCase.getSongsPreview())
}