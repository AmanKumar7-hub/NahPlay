package com.example.nahplay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nahplay.ui.theme.Black
import com.example.nahplay.ui.theme.TextMuted
import com.example.nahplay.ui.theme.TextSecondary
import com.example.nahplay.ui.theme.White
import com.example.nahplay.viewModel.MusicViewModel

@Composable
fun PlaylistsScreen(
    viewModel: MusicViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(paddingValues)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text  = "Playlists",
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
            Text(
                text  = "Coming soon with Room DB",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Box(
            modifier         = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "♪", style = MaterialTheme.typography.headlineLarge, color = TextMuted)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "No playlists yet", style = MaterialTheme.typography.titleMedium, color = TextMuted)
                Text(
                    text     = "Playlists will be added once Room DB is set up",
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = TextMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}