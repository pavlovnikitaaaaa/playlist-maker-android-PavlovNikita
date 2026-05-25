package com.prcom.practicum.playlistproj.domain

import com.prcom.practicum.playlistproj.data.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun getHistory(): Flow<List<Track>>
    suspend fun addToHistory(track: Track)
    suspend fun clearHistory()
}
