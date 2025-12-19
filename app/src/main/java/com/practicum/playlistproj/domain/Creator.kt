package com.prcom.practicum.playlistproj.domain

import com.prcom.practicum.playlistproj.creator.Storage
import com.prcom.practicum.playlistproj.data.network.RetrofitNetworkClient
import com.prcom.practicum.playlistproj.data.network.TracksRepositoryImpl

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}