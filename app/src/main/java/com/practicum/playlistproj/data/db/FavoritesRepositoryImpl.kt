package com.prcom.practicum.playlistproj.data.db

import com.prcom.practicum.playlistproj.data.db.dao.FavoriteTrackDao
import com.prcom.practicum.playlistproj.data.db.entity.FavoriteTrackEntity
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.domain.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val favoriteTrackDao: FavoriteTrackDao
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<Track>> =
        favoriteTrackDao.getAll().map { list -> list.map { it.toTrack() } }

    override fun getFavoriteIds(): Flow<List<Long>> = favoriteTrackDao.getFavoriteIds()

    override suspend fun addToFavorites(track: Track) {
        favoriteTrackDao.insert(
            FavoriteTrackEntity(
                trackId = track.trackId,
                trackName = track.trackName,
                artistName = track.artistName,
                trackTime = track.trackTime,
                artworkUrl = track.artworkUrl,
                addedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun removeFromFavorites(trackId: Long) {
        favoriteTrackDao.deleteById(trackId)
    }

    private fun FavoriteTrackEntity.toTrack() = Track(
        trackId = trackId,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl = artworkUrl
    )
}
