package com.prcom.practicum.playlistproj.ui.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.domain.Creator
import com.prcom.practicum.playlistproj.domain.FavoritesRepository
import com.prcom.practicum.playlistproj.domain.Playlist
import com.prcom.practicum.playlistproj.domain.PlaylistsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrackDetailsViewModel(
    private val trackId: Long,
    private val favoritesRepository: FavoritesRepository,
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val isFavorite = favoritesRepository.getFavoriteIds()
        .map { ids -> ids.contains(trackId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val playlists = playlistsRepository.getPlaylists().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun toggleFavorite(track: Track) {
        viewModelScope.launch {
            if (isFavorite.value) {
                favoritesRepository.removeFromFavorites(track.trackId)
            } else {
                favoritesRepository.addToFavorites(track)
            }
        }
    }

    fun addToPlaylist(playlist: Playlist, track: Track, onResult: (added: Boolean) -> Unit) {
        viewModelScope.launch {
            val added = playlistsRepository.addTrackToPlaylist(playlist.id, track)
            onResult(added)
        }
    }

    companion object {
        fun getViewModelFactory(trackId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TrackDetailsViewModel(
                        trackId,
                        Creator.getFavoritesRepository(),
                        Creator.getPlaylistsRepository()
                    ) as T
                }
            }
    }
}
