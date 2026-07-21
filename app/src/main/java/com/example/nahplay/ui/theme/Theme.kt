package com.example.nahplay.ui.theme

import android.app.Activity
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.SystemBarStyle.Companion.dark
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NahDarkColorScheme = darkColorScheme(
    primary = Purple,
    onPrimary = White,
    primaryContainer = PurpleDark,
    secondary = Cyan,
    onSecondary = Black,
    background = Black,
    onBackground = White,
    surface = BlackSurface,
    onSurface = White,
    surfaceVariant = BlackCard,
    outline = DarkBorder,
    error = Color(0xFFEF4444)

)


@Composable
fun NahPlayTheme(content: @Composable ()-> Unit){
    val view = LocalView.current

    if(!view.isInEditMode){
        SideEffect{
            val window = (view.context as Activity).window

            WindowCompat.setDecorFitsSystemWindows(window,false)

            if(Build.VERSION.SDK_INT<35){
                @Suppress("DEPRICATION")
                window.statusBarColor = Black.toArgb()
            }

            WindowCompat.getInsetsController(window,view).apply{
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }
    MaterialTheme(
        colorScheme = NahDarkColorScheme,
        typography = NahTypography,
        content =content
    )
}

