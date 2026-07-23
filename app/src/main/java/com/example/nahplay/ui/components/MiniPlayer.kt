package com.example.nahplay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.theme.BlackCard
import com.example.nahplay.ui.theme.BlackElevated
import com.example.nahplay.ui.theme.Purple
import com.example.nahplay.ui.theme.TextSecondary
import com.example.nahplay.ui.theme.White
import com.example.nahplay.viewModel.MusicViewModel
import com.example.nahplay.viewModel.PlaybackState

import com.example.nahplay.R

@Composable
fun MiniPlayer(
    viewModel: MusicViewModel,
    playerController: PlayerController,
    onExpandClick:()->Unit,
    modifier: Modifier = Modifier
){
    val currentSong by viewModel.currentSong.collectAsState()
    val playbackState  by viewModel.playbackState.collectAsState()
    val progressMs     by viewModel.progressMs.collectAsState()
    val durationMs     by viewModel.durationMs.collectAsState()

    val song = currentSong ?: return

    val progress = if (durationMs > 0)
        (progressMs.toFloat() / durationMs.toFloat()).coerceIn(0f, 1f)
    else 0f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(BlackCard)
            .clickable { onExpandClick() }
    ) {
        // Progress bar at the top
        LinearProgressIndicator(
            progress        = { progress },
            modifier        = Modifier.fillMaxWidth().height(2.dp),
            color           = Purple,
            trackColor      = BlackElevated
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album art
            AlbumArt(
                uri = song.albumArtUri,
                size = 42.dp,
                cornerRadius = 8.dp,
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Song info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text     = song.title,
                    style    = MaterialTheme.typography.titleMedium,
                    color    = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text     = song.artist,
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                IconButton(onClick = { playerController.playPrevious() }) {
                    Icon(
                        painter = painterResource(R.drawable.material_icon_skip_previous),
//                        imageVector        = Icons.Filled.SkipPrevious,
                        contentDescription = "Previous",
                        tint               = White,
                        modifier           = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = { playerController.togglePlayPause() }) {
                    Icon(
                        painter = if (playbackState == PlaybackState.PLAYING)
                            painterResource(R.drawable.material_icon_pause)
                        else
                            painterResource(R.drawable.material_icon_play),
                        contentDescription = "Play/Pause",
                        tint     = Purple,
                        modifier = Modifier.size(28.dp)
                    )
                }

                IconButton(onClick = { playerController.playNext() }) {
                    Icon(
                        painter = painterResource(R.drawable.material_icon_skip_next),
                        contentDescription = "Next",
                        tint               = White,
                        modifier           = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}