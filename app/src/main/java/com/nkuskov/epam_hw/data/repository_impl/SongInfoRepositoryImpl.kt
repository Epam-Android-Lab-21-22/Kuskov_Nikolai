package com.nkuskov.epam_hw.data.repository_impl

import com.nkuskov.epam_hw.data.database.SongInfoDB
import com.nkuskov.epam_hw.domain.domain_model.SongInfo
import com.nkuskov.epam_hw.domain.repository_interface.ISongInfoRepository

class SongInfoRepositoryImpl : ISongInfoRepository {
    override fun getSongInfoById(id: String): SongInfo? {
        return SongInfoDB.data.find { songInfo -> songInfo.id == id };
    }
}