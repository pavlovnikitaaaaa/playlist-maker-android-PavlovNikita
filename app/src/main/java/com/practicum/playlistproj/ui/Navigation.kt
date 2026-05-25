package com.prcom.practicum.playlistproj.ui

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.ui.favorites.FavoritesScreen
import com.prcom.practicum.playlistproj.ui.favorites.FavoritesViewModel
import com.prcom.practicum.playlistproj.ui.playlists.CreatePlaylistScreen
import com.prcom.practicum.playlistproj.ui.playlists.CreatePlaylistViewModel
import com.prcom.practicum.playlistproj.ui.playlists.PlaylistDetailsScreen
import com.prcom.practicum.playlistproj.ui.playlists.PlaylistDetailsViewModel
import com.prcom.practicum.playlistproj.ui.playlists.PlaylistsScreen
import com.prcom.practicum.playlistproj.ui.playlists.PlaylistsViewModel
import com.prcom.practicum.playlistproj.ui.search.SearchScreen
import com.prcom.practicum.playlistproj.ui.search.SearchViewModel
import com.prcom.practicum.playlistproj.ui.settings.SettingsScreen
import com.prcom.practicum.playlistproj.ui.track.TrackDetailsScreen
import com.prcom.practicum.playlistproj.ui.track.TrackDetailsViewModel

object Routes {
    const val MAIN = "main"
    const val SEARCH = "search"
    const val SETTINGS = "settings"
    const val PLAYLISTS = "playlists"
    const val CREATE_PLAYLIST = "create_playlist"
    const val PLAYLIST_DETAILS = "playlist_details/{playlistId}"
    const val FAVORITES = "favorites"
    const val TRACK_DETAILS = "track_details/{trackId}/{name}/{artist}/{time}/{artwork}"

    fun playlistDetails(id: Long) = "playlist_details/$id"

    const val NO_ARTWORK = "none"

    fun trackDetails(track: Track): String {
        val name = Uri.encode(track.trackName.ifBlank { "_" })
        val artist = Uri.encode(track.artistName.ifBlank { "_" })
        val time = Uri.encode(track.trackTime.ifBlank { "_" })
        val artwork = Uri.encode(track.artworkUrl ?: NO_ARTWORK)
        return "track_details/${track.trackId}/$name/$artist/$time/$artwork"
    }
}

@Composable
fun PlaylistHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(
                onNavigateToSearch = { navController.navigate(Routes.SEARCH) },
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToPlaylists = { navController.navigate(Routes.PLAYLISTS) },
                onNavigateToFavorites = { navController.navigate(Routes.FAVORITES) }
            )
        }

        composable(Routes.SEARCH) {
            val searchViewModel: SearchViewModel = viewModel(
                factory = SearchViewModel.getViewModelFactory()
            )
            SearchScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = searchViewModel,
                onNavigateBack = { navController.popBackStack() },
                onTrackClick = { track ->
                    searchViewModel.onTrackClicked(track)
                    navController.navigate(Routes.trackDetails(track))
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Routes.PLAYLISTS) {
            val playlistsViewModel: PlaylistsViewModel = viewModel(
                factory = PlaylistsViewModel.getViewModelFactory()
            )
            PlaylistsScreen(
                viewModel = playlistsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onCreatePlaylist = { navController.navigate(Routes.CREATE_PLAYLIST) },
                onPlaylistClick = { id -> navController.navigate(Routes.playlistDetails(id)) }
            )
        }

        composable(Routes.CREATE_PLAYLIST) {
            val createViewModel: CreatePlaylistViewModel = viewModel(
                factory = CreatePlaylistViewModel.getViewModelFactory()
            )
            CreatePlaylistScreen(
                viewModel = createViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.PLAYLIST_DETAILS,
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            val detailsViewModel: PlaylistDetailsViewModel = viewModel(
                factory = PlaylistDetailsViewModel.getViewModelFactory(playlistId)
            )
            PlaylistDetailsScreen(
                viewModel = detailsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onTrackClick = { track -> navController.navigate(Routes.trackDetails(track)) }
            )
        }

        composable(Routes.FAVORITES) {
            val favoritesViewModel: FavoritesViewModel = viewModel(
                factory = FavoritesViewModel.getViewModelFactory()
            )
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onNavigateBack = { navController.popBackStack() },
                onTrackClick = { track -> navController.navigate(Routes.trackDetails(track)) }
            )
        }

        composable(
            route = Routes.TRACK_DETAILS,
            arguments = listOf(
                navArgument("trackId") { type = NavType.LongType },
                navArgument("name") { type = NavType.StringType },
                navArgument("artist") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("artwork") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments
            val artwork = args?.getString("artwork").orEmpty()
            val track = Track(
                trackId = args?.getLong("trackId") ?: 0L,
                trackName = args?.getString("name").orEmpty(),
                artistName = args?.getString("artist").orEmpty(),
                trackTime = args?.getString("time").orEmpty(),
                artworkUrl = if (artwork == Routes.NO_ARTWORK || artwork.isBlank()) null else artwork
            )
            val detailsViewModel: TrackDetailsViewModel = viewModel(
                factory = TrackDetailsViewModel.getViewModelFactory(track.trackId)
            )
            TrackDetailsScreen(
                track = track,
                viewModel = detailsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
