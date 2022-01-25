package com.nkuskov.epam_hw.domain.usecases

import com.nkuskov.epam_hw.domain.domain_model.SongInfo
import com.nkuskov.epam_hw.domain.repository_interface.ISongInfoRepository

class GetSongInfoByIdUseCase(private val repository: ISongInfoRepository) {
    fun getSongInfoById(id: String) : SongInfo? = repository.getSongInfoById(id)
}