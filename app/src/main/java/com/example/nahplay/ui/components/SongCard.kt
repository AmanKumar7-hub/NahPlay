package com.example.nahplay.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nahplay.R
import com.example.nahplay.data.Song
import com.example.nahplay.ui.theme.Purple
import com.example.nahplay.ui.theme.TextMuted
import com.example.nahplay.ui.theme.TextSecondary
import com.example.nahplay.ui.theme.White

@Composable
fun SongCard(
    song: Song,
    isFavourite: Boolean,
    isPlaying:Boolean,
    onSongClick:(Song)-> Unit,
    onFavouriteClick:(Song)->Unit,
    modifier: Modifier = Modifier
    ){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable{onSongClick(song)}
            .padding(horizontal=16.dp, vertical  = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AlbumArt(song.albumArtUri,
            size=48.dp)

        Spacer(modifier = Modifier.width(12.dp))

        // Song info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text     = song.title,
                style    = MaterialTheme.typography.titleMedium,
                color    = if (isPlaying) Purple else White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text     = song.artist,
                style    = MaterialTheme.typography.bodyMedium,
                color    = if (isPlaying) Purple.copy(alpha = 0.7f) else TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Favorite button
        IconButton(onClick = { onFavouriteClick(song) }) {
            Icon(
                painter = if (isFavourite)
                    painterResource(R.drawable.ic_heart)
                else
                    painterResource(R.drawable.ic_heart),
                contentDescription = "Favorite",
                tint = if (isFavourite) Purple else TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }

        // More options
        IconButton(onClick = { /* later */ }) {
            Icon(
                painter=painterResource(R.drawable.ic_morevert_copy),
                contentDescription = "More",
                tint               = TextMuted,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}