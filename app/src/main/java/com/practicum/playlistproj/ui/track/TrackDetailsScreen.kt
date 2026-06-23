package com.prcom.practicum.playlistproj.ui.track

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.prcom.practicum.playlistproj.R
import com.prcom.practicum.playlistproj.data.network.Track
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    track: Track,
    viewModel: TrackDetailsViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val playlists by viewModel.playlists.collectAsStateWithLifecycle()

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = Color(0xFF1A1B22)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = track.artworkUrl,
            contentDescription = track.trackName,
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
            fallback = painterResource(id = R.drawable.ic_music),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = track.trackName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1B22)
        )
        Text(
            text = track.artistName,
            fontSize = 16.sp,
            color = Color(0xFF7A7C81)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Длительность: ${track.trackTime}",
            fontSize = 14.sp,
            color = Color(0xFF1A1B22)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.toggleFavorite(track) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "В избранное",
                    tint = if (isFavorite) Color(0xFFE53935) else Color(0xFF1A1B22),
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { showSheet = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить в плейлист",
                    tint = Color(0xFF1A1B22),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 24.dp)) {
                Text(
                    text = "Добавить в плейлист",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1B22),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                if (playlists.isEmpty()) {
                    Text(
                        text = "Пока нет ни одного плейлиста",
                        color = Color(0xFF7A7C81),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(playlists.size) { index ->
                            val playlist = playlists[index]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.addToPlaylist(playlist, track) { added ->
                                            val message = if (added) {
                                                "Добавлено в плейлист ${playlist.name}"
                                            } else {
                                                "Трек уже добавлен в плейлист ${playlist.name}"
                                            }
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        }
                                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                                            showSheet = false
                                        }
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = playlist.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1A1B22)
                                    )
                                    Text(
                                        text = "${playlist.trackCount} треков",
                                        fontSize = 12.sp,
                                        color = Color(0xFF7A7C81)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
