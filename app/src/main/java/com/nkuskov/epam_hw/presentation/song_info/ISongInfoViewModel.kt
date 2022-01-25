package com.nkuskov.epam_hw.presentation.song_info

import androidx.lifecycle.LiveData
import com.nkuskov.epam_hw.domain.domain_model.SongInfo

interface ISongInfoViewModel {
    val onSongInfoGot: LiveData<SongInfo>
}