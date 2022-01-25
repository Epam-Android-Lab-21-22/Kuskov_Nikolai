package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.domain_model.SongPreview
import com.nkuskov.epam_hw.domain.repository_interface.ISongsPreviewRepository

class GetSongPreviewsUseCase(private val repository: ISongsPreviewRepository) {

    fun getSongsPreview() : Set<SongPreview> = repository.getSongsPreview()
}