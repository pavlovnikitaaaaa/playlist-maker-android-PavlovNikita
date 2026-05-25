package com.prcom.practicum.playlistproj.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.ui.item.TrackRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onNavigateBack: () -> Unit,
    onTrackClick: (Track) -> Unit
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное", fontSize = 22.sp, color = Color(0xFF1A1B22)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color(0xFF1A1B22)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Список избранного пуст", color = Color(0xFF7A7C81))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(favorites.size) { index ->
                    val track = favorites[index]
                    TrackRow(
                        track = track,
                        onClick = { onTrackClick(track) },
                        onLongClick = { trackToDelete = track }
                    )
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
    }

    val deleting = trackToDelete
    if (deleting != null) {
        AlertDialog(
            onDismissRequest = { trackToDelete = null },
            title = { Text("Удалить из избранного") },
            text = { Text("Удалить трек «${deleting.trackName}» из избранного?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.removeFromFavorites(deleting)
                    trackToDelete = null
                }) { Text("Удалить") }
            },
            dismissButton = {
                TextButton(onClick = { trackToDelete = null }) { Text("Отмена") }
            }
        )
    }
}
