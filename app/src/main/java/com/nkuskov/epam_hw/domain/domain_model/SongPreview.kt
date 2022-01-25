package com.nkuskov.epam_hw.domain.domain_model

data class SongPreview(
    val id: String,
    val title: String,
    val performer: String,
    val coverUrl: String?
)
