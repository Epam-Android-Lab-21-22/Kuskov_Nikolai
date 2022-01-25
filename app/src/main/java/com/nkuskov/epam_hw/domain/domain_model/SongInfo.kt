package com.nkuskov.epam_hw.domain.domain_model

data class SongInfo(
    val id: String,
    val title: String,
    val performer: String,
    val coverUrl: String?,
    val text: String,
    val year: Int,
    val album: String,
    val genre: String
)
