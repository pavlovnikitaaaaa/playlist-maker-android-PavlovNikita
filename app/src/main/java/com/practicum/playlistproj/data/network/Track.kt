package com.prcom.practicum.playlistproj.data.network

data class Track(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl: String? = null
)
