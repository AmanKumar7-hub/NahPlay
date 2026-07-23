package com.example.nahplay.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nahplay.R
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.components.SongCard
import com.example.nahplay.ui.theme.Black
import com.example.nahplay.ui.theme.BlackCard
import com.example.nahplay.ui.theme.TextMuted
import com.example.nahplay.ui.theme.TextSecondary
import com.example.nahplay.viewModel.MusicViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun LibraryScreen(
    viewModel: MusicViewModel,
    playerController: PlayerController,
    paddingValues: PaddingValues
){
    val songs by viewModel.filteredSongs.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val favourites by viewModel.favorites.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize()
                .background(Black)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text  = "Library",
                    style = MaterialTheme.typography.headlineLarge,
                    color = White
                )
                Text(
                    text  = "${songs.size} songs on device",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Search Option
            TextField(
                value         = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                placeholder   = {
                    Text(
                        "Search songs, artists…",
                        color = TextMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        tint = TextMuted
                    )
                },
                singleLine = true,
                shape      = RoundedCornerShape(24.dp),
                colors     = TextFieldDefaults.colors(
                    focusedContainerColor   = BlackCard,
                    unfocusedContainerColor = BlackCard,
                    focusedTextColor        = White,
                    unfocusedTextColor      = White,
                    focusedIndicatorColor   = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor             = White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Text(
                text     = if (searchQuery.isBlank()) "ALL SONGS" else "RESULTS",
                style    = MaterialTheme.typography.labelSmall,
                color    = TextMuted,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = songs,
                    key = {it.id},

                ){
                    song->
                    SongCard(
                        song = song,
                        isFavourite = favourites.any{it.id == song.id},
                        isPlaying       = currentSong?.id == song.id,
                        onSongClick     = { playerController.play(song) },
                        onFavouriteClick = { viewModel.toggleFavorite(song) }
                    )
                }
            }
        }
}