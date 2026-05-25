package com.prcom.practicum.playlistproj.ui.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.prcom.practicum.playlistproj.R
import com.prcom.practicum.playlistproj.data.network.Track
import com.prcom.practicum.playlistproj.ui.item.TrackRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsScreen(
    viewModel: PlaylistDetailsViewModel,
    onNavigateBack: () -> Unit,
    onTrackClick: (Track) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.playlist?.name ?: "Плейлист",
                        fontSize = 22.sp,
                        color = Color(0xFF1A1B22)
                    )
                },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val playlist = state.playlist
            if (playlist != null) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    AsyncImage(
                        model = playlist.coverPath,
                        contentDescription = playlist.name,
                        placeholder = painterResource(id = R.drawable.ic_music),
                        error = painterResource(id = R.drawable.ic_music),
                        fallback = painterResource(id = R.drawable.ic_music),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF2F2F2))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = playlist.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1B22)
                    )
                    if (playlist.description.isNotBlank()) {
                        Text(
                            text = playlist.description,
                            fontSize = 14.sp,
                            color = Color(0xFF7A7C81)
                        )
                    }
                    Text(
                        text = "${playlist.trackCount} треков",
                        fontSize = 14.sp,
                        color = Color(0xFF7A7C81)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (state.tracks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("В этом плейлисте пока нет треков", color = Color(0xFF7A7C81))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.tracks.size) { index ->
                        val track = state.tracks[index]
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
    }

    val deleting = trackToDelete
    if (deleting != null) {
        AlertDialog(
            onDismissRequest = { trackToDelete = null },
            title = { Text("Удалить трек") },
            text = { Text("Хотите удалить трек «${deleting.trackName}» из плейлиста?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.removeTrack(deleting)
                    trackToDelete = null
                }) { Text("Удалить") }
            },
            dismissButton = {
                TextButton(onClick = { trackToDelete = null }) { Text("Отмена") }
            }
        )
    }
}
