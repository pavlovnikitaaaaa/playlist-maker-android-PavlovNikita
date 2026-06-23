package com.prcom.practicum.playlistproj.data.db

import com.prcom.practicum.playlistproj.data.db.dao.PlaylistDao
import com.prcom.practicum.playlistproj.data.db.dao.PlaylistTrackDao
import com.prcom.practicum.playlistproj.data.db.entity.PlaylistEntity
import com.prcom.practicum.playlistproj.data.db.entity.PlaylistTrackEntity
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.domain.Playlist
import com.prcom.practicum.playlistproj.domain.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistTrackDao: PlaylistTrackDao
) : PlaylistsRepository {

    override fun getPlaylists(): Flow<List<Playlist>> =
        playlistDao.getAll().map { list -> list.map { it.toPlaylist() } }

    override fun observePlaylist(id: Long): Flow<Playlist?> =
        playlistDao.observeById(id).map { it?.toPlaylist() }

    override suspend fun createPlaylist(
        name: String,
        description: String,
        coverPath: String?
    ): Long {
        return playlistDao.insert(
            PlaylistEntity(
                name = name,
                description = description,
                coverPath = coverPath,
                trackIds = emptyList(),
                trackCount = 0
            )
        )
    }

    override suspend fun addTrackToPlaylist(playlistId: Long, track: Track): Boolean {
        val playlist = playlistDao.getById(playlistId) ?: return false
        if (playlist.trackIds.contains(track.trackId)) return false

        playlistTrackDao.insert(
            PlaylistTrackEntity(
                trackId = track.trackId,
                trackName = track.trackName,
                artistName = track.artistName,
                trackTime = track.trackTime,
                artworkUrl = track.artworkUrl,
                addedAt = System.currentTimeMillis()
            )
        )
        val newIds = listOf(track.trackId) + playlist.trackIds
        playlistDao.update(
            playlist.copy(trackIds = newIds, trackCount = newIds.size)
        )
        return true
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long) {
        val playlist = playlistDao.getById(playlistId) ?: return
        val newIds = playlist.trackIds.filterNot { it == trackId }
        playlistDao.update(
            playlist.copy(trackIds = newIds, trackCount = newIds.size)
        )

        val stillUsed = playlistDao.getAllOnce().any { it.id != playlistId && it.trackIds.contains(trackId) }
        if (!stillUsed) {
            playlistTrackDao.deleteById(trackId)
        }
    }

    override suspend fun getTracks(ids: List<Long>): List<Track> {
        if (ids.isEmpty()) return emptyList()
        val entities = playlistTrackDao.getByIds(ids).associateBy { it.trackId }
        return ids.mapNotNull { id -> entities[id]?.toTrack() }
    }

    private fun PlaylistEntity.toPlaylist() = Playlist(
        id = id,
        name = name,
        description = description,
        coverPath = coverPath,
        trackIds = trackIds,
        trackCount = trackCount
    )

    private fun PlaylistTrackEntity.toTrack() = Track(
        trackId = trackId,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl = artworkUrl
    )
}
