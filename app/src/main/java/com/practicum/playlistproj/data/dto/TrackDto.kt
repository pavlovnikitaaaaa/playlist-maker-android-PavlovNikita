package com.prcom.practicum.playlistproj.data.dto

data class TrackDto(
    val trackId: Long = 0,
    val trackName: String = "",
    val artistName: String = "",
    val trackTimeMillis: Int = 0,
    val artworkUrl100: String? = null
)
