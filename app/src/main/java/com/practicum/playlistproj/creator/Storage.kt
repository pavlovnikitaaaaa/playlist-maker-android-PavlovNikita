package com.prcom.practicum.playlistproj.creator

import com.prcom.practicum.playlistproj.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackId = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Тролль",
            trackTimeMillis = 158000
        ),
        TrackDto(
            trackId = 2,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000
        ),
        TrackDto(
            trackId = 3,
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000
        ),
        TrackDto(
            trackId = 4,
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000
        ),
        TrackDto(
            trackId = 5,
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000
        ),
        TrackDto(
            trackId = 6,
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000
        ),
        TrackDto(
            trackId = 7,
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000
        ),
        TrackDto(
            trackId = 8,
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000
        ),
        TrackDto(
            trackId = 9,
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000
        ),
        TrackDto(
            trackId = 10,
            trackName = "Чёрный бумер",
            artistName = "Серёга",
            trackTimeMillis = 241000
        )
    )

    fun search(request: String): List<TrackDto> {
        if (request.isBlank()) return emptyList()
        val query = request.lowercase()
        return listTracks.filter {
            it.trackName.lowercase().contains(query) ||
                it.artistName.lowercase().contains(query)
        }
    }
}
