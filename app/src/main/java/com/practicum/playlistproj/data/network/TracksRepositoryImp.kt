package com.prcom.practicum.playlistproj.data.network

import com.prcom.practicum.playlistproj.data.dto.TracksSearchRequest
import com.prcom.practicum.playlistproj.data.dto.TracksSearchResponse
import com.prcom.practicum.playlistproj.data.history.SearchHistory
import com.prcom.practicum.playlistproj.domain.NetworkClient
import com.prcom.practicum.playlistproj.domain.TracksRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val searchHistory: SearchHistory
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as TracksSearchResponse).results.map { dto ->
                val seconds = dto.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds % 60)
                Track(
                    trackId = dto.trackId,
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = trackTime,
                    artworkUrl = dto.artworkUrl100
                )
            }
        } else {
            throw IOException("Search failed with code ${response.resultCode}")
        }
    }

    override fun getHistory(): Flow<List<Track>> = searchHistory.history

    override suspend fun addToHistory(track: Track) = searchHistory.add(track)

    override suspend fun clearHistory() = searchHistory.clear()
}
