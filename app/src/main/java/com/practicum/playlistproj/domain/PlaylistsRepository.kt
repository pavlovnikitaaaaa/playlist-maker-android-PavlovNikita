package com.prcom.practicum.playlistproj.domain

import com.prcom.practicum.playlistproj.data.network.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getPlaylists(): Flow<List<Playlist>>
    fun observePlaylist(id: Long): Flow<Playlist?>
    suspend fun createPlaylist(name: String, description: String, coverPath: String?): Long
    suspend fun addTrackToPlaylist(playlistId: Long, track: Track): Boolean
    suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long)
    suspend fun getTracks(ids: List<Long>): List<Track>
}
