package com.prcom.practicum.playlistproj.data.dto

data class ItunesSearchResponse(
    val resultCount: Int = 0,
    val results: List<TrackDto> = emptyList()
)
