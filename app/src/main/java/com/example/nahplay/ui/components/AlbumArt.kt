package com.example.nahplay.ui.components


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.nahplay.R
import com.example.nahplay.ui.theme.Cyan
import com.example.nahplay.ui.theme.Purple

@Composable
fun AlbumArt(
    uri: Uri?,
    modifier: Modifier =Modifier,
    size: Dp = 48.dp,
    cornerRadius: Dp = 10.dp
){
    val shape = RoundedCornerShape(cornerRadius)

    SubcomposeAsyncImage(
        model = uri,
        contentDescription = "Album Art",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(shape),
        loading= {GradientPlaceholder(shape, size)},
        error ={GradientPlaceholder(shape, size)}
    )
}

@Composable
private fun GradientPlaceholder(
    shape: RoundedCornerShape,
    size : Dp
){
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background (
                brush= Brush.linearGradient(
                    colors= listOf(Purple, Cyan)
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            painter = painterResource(R.drawable.material_icon_music_note),
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(size*0.4f)
        )
    }

}