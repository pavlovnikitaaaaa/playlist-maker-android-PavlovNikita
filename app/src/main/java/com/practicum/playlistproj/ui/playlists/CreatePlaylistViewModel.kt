package com.prcom.practicum.playlistproj.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prcom.practicum.playlistproj.domain.Creator
import com.prcom.practicum.playlistproj.domain.PlaylistsRepository
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    fun createPlaylist(
        name: String,
        description: String,
        coverPath: String?,
        onCreated: () -> Unit
    ) {
        viewModelScope.launch {
            playlistsRepository.createPlaylist(name.trim(), description.trim(), coverPath)
            onCreated()
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CreatePlaylistViewModel(Creator.getPlaylistsRepository()) as T
                }
            }
    }
}
