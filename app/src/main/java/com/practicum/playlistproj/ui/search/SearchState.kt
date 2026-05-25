package com.prcom.practicum.playlistproj.ui.search

import com.prcom.practicum.playlistproj.data.network.Track

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    data class Success(val list: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()

}