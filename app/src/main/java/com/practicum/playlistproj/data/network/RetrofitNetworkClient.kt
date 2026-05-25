package com.prcom.practicum.playlistproj.data.network

import com.prcom.practicum.playlistproj.creator.Storage
import com.prcom.practicum.playlistproj.data.dto.TracksSearchRequest
import com.prcom.practicum.playlistproj.data.dto.TracksSearchResponse
import com.prcom.practicum.playlistproj.domain.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(request: Any): TracksSearchResponse {
        val searchList = storage.search((request as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}