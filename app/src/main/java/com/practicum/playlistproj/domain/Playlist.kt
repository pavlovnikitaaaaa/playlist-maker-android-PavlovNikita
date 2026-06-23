package com.prcom.practicum.playlistproj.domain

data class Playlist(
    val id: Long,
    val name: String,
    val description: String,
    val coverPath: String?,
    val trackIds: List<Long>,
    val trackCount: Int
)
