package com.example.nahplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nahplay.R
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.components.AlbumArt
import com.example.nahplay.ui.theme.Black
import com.example.nahplay.ui.theme.Cyan
import com.example.nahplay.ui.theme.Purple
import com.example.nahplay.ui.theme.TextMuted
import com.example.nahplay.ui.theme.TextSecondary
import com.example.nahplay.ui.theme.White
import com.example.nahplay.viewModel.MusicViewModel
import com.example.nahplay.viewModel.PlaybackState

@Composable
fun NowPlayingScreen(
    viewModel: MusicViewModel,
    playerController: PlayerController,
    onBack:()->Unit,
    paddingValues: PaddingValues
){
    val currentSong by viewModel.currentSong.collectAsState()
    val playbackState by viewModel.playbackState.collectAsState()
    val progressMs    by viewModel.progressMs.collectAsState()
    val durationMs    by viewModel.durationMs.collectAsState()
    val favorites     by viewModel.favorites.collectAsState()

    val song = currentSong ?: return
    val progress = if(durationMs>0)
        (progressMs.toFloat()/durationMs.toFloat()).coerceIn(0f, 1f)
    else 0f


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.keyboardarrowdown),
                    contentDescription = "Back",
                    tint               = White,
                    modifier           = Modifier.size(28.dp)
                )
            }
            Text(
                text  = "NOW PLAYING",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
            IconButton(onClick = { viewModel.toggleFavorite(song) }) {
                Icon(
                    painter = if (favorites.any { it.id == song.id })
                        painterResource(R.drawable.ic_heart)
                    else
                         painterResource(R.drawable.ic_heart),
                    contentDescription = "Favorite",
                    tint = Purple,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Album art — large
        AlbumArt(
            uri          = song.albumArtUri,
            size         = 280.dp,
            cornerRadius = 20.dp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Song title & artist
        Text(
            text  = song.title,
            style = MaterialTheme.typography.titleLarge,
            color = White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text  = "${song.artist} • ${song.album}",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Seek bar
        Slider(
            value         = progress,
            onValueChange = { playerController.seekTo((it * durationMs).toLong()) },
            modifier      = Modifier.fillMaxWidth(),
            colors        = SliderDefaults.colors(
                thumbColor                = White,
                activeTrackColor          = Purple,
                inactiveTrackColor        = TextMuted
            )
        )

        // Timestamps
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text  = viewModel.formatDuration(progressMs),
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
            Text(
                text  = viewModel.formatDuration(durationMs),
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Controls
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            // Shuffle
            IconButton(onClick = { /* shuffle — coming soon */ }) {
                Icon(
                    painter = painterResource(R.drawable.shuffle),
                    contentDescription = "Shuffle",
                    tint     = TextMuted,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Previous
            IconButton(onClick = { playerController.playPrevious() }) {
                Icon(
                    painter = painterResource(R.drawable.material_icon_skip_previous),
                    contentDescription = "Previous",
                    tint     = White,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Play / Pause — gradient circle button
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(Purple, Cyan))
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { playerController.togglePlayPause() }) {
                    Icon(
                        painter = if (playbackState == PlaybackState.PLAYING)
                            painterResource(R.drawable.pause)
                        else
                            painterResource(R.drawable.material_icon_play),
                        contentDescription = "Play/Pause",
                        tint     = White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Next
            IconButton(onClick = { playerController.playNext() }) {
                Icon(
                    painterResource(R.drawable.material_icon_skip_next),
                    contentDescription = "Next",
                    tint     = White,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Repeat
            IconButton(onClick = { /* repeat — coming soon */ }) {
                Icon(
                    painter = painterResource(R.drawable.repeat),
                    contentDescription = "Repeat",
                    tint     = TextMuted,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}