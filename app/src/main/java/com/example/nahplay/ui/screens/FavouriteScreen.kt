package com.example.nahplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.components.SongCard
import com.example.nahplay.ui.theme.Black
import com.example.nahplay.ui.theme.TextMuted
import com.example.nahplay.ui.theme.TextSecondary
import com.example.nahplay.ui.theme.White
import com.example.nahplay.viewModel.MusicViewModel

@Composable
fun FavoritesScreen(
    viewModel: MusicViewModel,
    playerController: PlayerController,
    paddingValues: PaddingValues
) {
    val favourites by viewModel.favorites.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(paddingValues)
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text  = "Favorites",
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
            Text(
                text  = "${favourites.size} songs",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (favourites.isEmpty()) {
            // Empty state
            Box(
                modifier         = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text  = "♡",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text  = "No favorites yet",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextMuted
                    )
                    Text(
                        text  = "Tap ♡ on any song to add it here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMuted,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        } else {
            Text(
                text     = "YOUR FAVORITES",
                style    = MaterialTheme.typography.labelSmall,
                color    = TextMuted,

            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = favourites,
                    key   = { it.id }
                ) { song ->
                    SongCard(
                        song            = song,
                        isFavourite      = true,
                        isPlaying       = currentSong?.id == song.id,
                        onSongClick     = { playerController.play(it) },
                        onFavouriteClick = { viewModel.toggleFavorite(it) }
                    )
                }
            }
        }
    }
}