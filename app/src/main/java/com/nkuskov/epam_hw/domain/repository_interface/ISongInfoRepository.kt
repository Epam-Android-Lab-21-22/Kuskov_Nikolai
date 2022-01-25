package com.nkuskov.epam_hw.domain.repository_interface

import com.nkuskov.epam_hw.domain.domain_model.SongInfo

interface ISongInfoRepository {

    fun getSongInfoById(id: String) : SongInfo?
}