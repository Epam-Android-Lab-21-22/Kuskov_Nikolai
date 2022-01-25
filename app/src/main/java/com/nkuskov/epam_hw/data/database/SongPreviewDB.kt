package com.nkuskov.epam_hw.data.database

import com.nkuskov.epam_hw.domain.domain_model.SongPreview

object SongPreviewDB {
    val data = setOf(
        SongPreview(
            id = "3A5907DE-C8E4-44B2-8B81-957068D8E66F",
            title = "Thunderstruck",
            performer = "AC/DC",
            coverUrl = "https://www.youtube.com/watch?v=e4Ao-iNPPUc"
        ),
        SongPreview(
            id = "559862B3-5486-4717-8EF8-4EF40370D95B",
            title = "Warriors of the world",
            performer = "Manowar",
            coverUrl = "https://www.youtube.com/watch?v=4qa5vHz7Zv8"
        ),
        SongPreview(
            id = "E751466A-7BF3-4E83-BA0D-4E6872515EAF",
            title = "Крылья",
            performer = "Наутилус Пампилус",
            coverUrl = "https://www.youtube.com/watch?v=koQ9sZz7wPg"
        ),
        SongPreview(
            id = "C0E73910-1FCF-43A4-8DC8-23DA2AC49168",
            title = "3 сентября",
            performer = "Михаил Шуфутинский",
            coverUrl = "https://www.youtube.com/watch?v=pv-Y-4vUBGA"
        ),
        SongPreview(
            id = "8BAE861B-1A32-4A9F-A73E-9352574FC482",
            title = "Enemy",
            performer = "Imagine Dragons",
            coverUrl = "https://www.youtube.com/watch?v=ymr4HKdNmlA"
        ),
        SongPreview(
            id = "87541946-C351-4F0C-9F8A-984F9884435B",
            title = "Rap God",
            performer = "Eminem",
            coverUrl = "https://www.youtube.com/watch?v=L53MZzuE0QY"
        ),
        SongPreview(
            id = "506DFBBA-AD90-4BA9-8C69-0A0C3B05428A",
            title = "Humanity",
            performer = "Scorpions",
            coverUrl = "https://www.youtube.com/watch?v=_QxXZv7Ysac"
        )
    )
}