package com.nkuskov.epam_hw.data.repository_impl

import com.nkuskov.epam_hw.data.database.SongPreviewDB
import com.nkuskov.epam_hw.domain.domain_model.SongPreview
import com.nkuskov.epam_hw.domain.repository_interface.ISongsPreviewRepository

class SongsPreviewRepository : ISongsPreviewRepository {
    override fun getSongsPreview(): Set<SongPreview> = SongPreviewDB.data
}