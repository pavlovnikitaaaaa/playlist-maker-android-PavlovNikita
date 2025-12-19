package com.prcom.practicum.playlistproj.data.network

import com.prcom.practicum.playlistproj.domain.NetworkClient
import com.prcom.practicum.playlistproj.data.dto.TracksSearchRequest
import com.prcom.practicum.playlistproj.data.dto.TracksSearchResponse
import com.prcom.practicum.playlistproj.domain.api.TracksRepository

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
                Track(it.trackName, it.artistName, trackTime) }
        } else {
            emptyList()
        }
    }
}