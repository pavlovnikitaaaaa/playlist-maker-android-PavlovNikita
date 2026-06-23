package com.prcom.practicum.playlistproj.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prcom.practicum.playlistproj.domain.Creator
import com.prcom.practicum.playlistproj.domain.PlaylistsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class PlaylistsViewModel(
    playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val playlists = playlistsRepository.getPlaylists().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(Creator.getPlaylistsRepository()) as T
                }
            }
    }
}
