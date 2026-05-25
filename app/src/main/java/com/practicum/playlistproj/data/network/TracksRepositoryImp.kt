package com.prcom.practicum.playlistproj.data.network

import com.prcom.practicum.playlistproj.data.dto.TracksSearchRequest
import com.prcom.practicum.playlistproj.data.dto.TracksSearchResponse
import com.prcom.practicum.playlistproj.domain.NetworkClient
import com.prcom.practicum.playlistproj.domain.TracksRepository
import com.prcom.practicum.playlistproj.data.history.SearchHistory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val searchHistory: SearchHistory
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { dto ->
                val seconds = dto.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
                Track(
                    trackId = dto.trackId,
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = trackTime,
                    artworkUrl = dto.artworkUrl
                )
            }
        } else {
            emptyList()
        }
    }

    override fun getHistory(): Flow<List<Track>> = searchHistory.history

    override suspend fun addToHistory(track: Track) = searchHistory.add(track)

    override suspend fun clearHistory() = searchHistory.clear()
}
