package com.prcom.practicum.playlistproj.creator

import com.prcom.practicum.playlistproj.data.network.TracksRepositoryImpl
import com.prcom.practicum.playlistproj.data.network.RetrofitNetworkClient
import com.prcom.practicum.playlistproj.domain.api.TrackSearchInteractor
import com.prcom.practicum.playlistproj.domain.api.TracksRepository
import com.prcom.practicum.playlistproj.domain.impl.TrackSearchInteractorImpl

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}