package com.prcom.practicum.playlistproj.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.domain.Creator
import com.prcom.practicum.playlistproj.domain.FavoritesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    val favorites = favoritesRepository.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun removeFromFavorites(track: Track) {
        viewModelScope.launch {
            favoritesRepository.removeFromFavorites(track.trackId)
        }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritesViewModel(Creator.getFavoritesRepository()) as T
                }
            }
    }
}
