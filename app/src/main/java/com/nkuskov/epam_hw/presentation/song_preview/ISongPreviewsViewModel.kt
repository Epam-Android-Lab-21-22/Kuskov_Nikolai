package com.nkuskov.epam_hw.presentation.song_preview

import androidx.lifecycle.LiveData
import com.nkuskov.epam_hw.domain.domain_model.SongPreview

interface ISongPreviewsViewModel {

    val songsPreviewUpdated: LiveData<Set<SongPreview>>
}