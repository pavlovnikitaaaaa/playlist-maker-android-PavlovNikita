package com.prcom.practicum.playlistproj.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.domain.Creator
import com.prcom.practicum.playlistproj.domain.Playlist
import com.prcom.practicum.playlistproj.domain.PlaylistsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PlaylistDetailsState(
    val playlist: Playlist? = null,
    val tracks: List<Track> = emptyList()
)

class PlaylistDetailsViewModel(
    private val playlistId: Long,
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {

    val state = playlistsRepository.observePlaylist(playlistId)
        .map { playlist ->
            if (playlist == null) {
                PlaylistDetailsState()
            } else {
                PlaylistDetailsState(
                    playlist = playlist,
                    tracks = playlistsRepository.getTracks(playlist.trackIds)
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlaylistDetailsState()
        )

    fun removeTrack(track: Track) {
        viewModelScope.launch {
            playlistsRepository.removeTrackFromPlaylist(playlistId, track.trackId)
        }
    }

    companion object {
        fun getViewModelFactory(playlistId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistDetailsViewModel(
                        playlistId,
                        Creator.getPlaylistsRepository()
                    ) as T
                }
            }
    }
}
