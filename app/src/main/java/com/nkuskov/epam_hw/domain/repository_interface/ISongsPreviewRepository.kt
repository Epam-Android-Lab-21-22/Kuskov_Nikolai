package com.nkuskov.epam_hw.domain.repository_interface

import com.nkuskov.epam_hw.domain.domain_model.SongPreview

interface ISongsPreviewRepository {

    fun getSongsPreview() : Set<SongPreview>
}