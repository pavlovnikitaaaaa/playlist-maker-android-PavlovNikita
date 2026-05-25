package com.prcom.practicum.playlistproj.data.dto

data class TrackDto(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl: String? = null
)
