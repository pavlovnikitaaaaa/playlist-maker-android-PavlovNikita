package com.prcom.practicum.playlistproj.domain

import com.prcom.practicum.playlistproj.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}