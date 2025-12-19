package com.prcom.practicum.playlistproj.domain.impl

import com.prcom.practicum.playlistproj.domain.api.TrackSearchInteractor
import com.prcom.practicum.playlistproj.domain.api.TracksRepository
import com.prcom.practicum.playlistproj.data.network.Track


class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor {

    override fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }
}