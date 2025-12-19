package com.prcom.practicum.playlistproj.domain.api

import com.prcom.practicum.playlistproj.data.network.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}