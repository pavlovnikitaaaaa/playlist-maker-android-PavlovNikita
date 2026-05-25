package com.prcom.practicum.playlistproj.domain

import com.prcom.practicum.playlistproj.data.network.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<Track>>
    fun getFavoriteIds(): Flow<List<Long>>
    suspend fun addToFavorites(track: Track)
    suspend fun removeFromFavorites(trackId: Long)
}
