package com.example.nahplay.navigation

import com.example.nahplay.R

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val screen: Screen
)

val bottomNavItems = listOf(
    BottomNavItem(
        label ="Library",
        icon =R.drawable.queue_music,
        screen = Screen.Library
    ),
    BottomNavItem(
        label="Favourites",
        icon=R.drawable.ic_heart,
        screen=Screen.Favourites
    ),
    BottomNavItem(
        label="Playlists",
        icon=R.drawable.list,
        screen= Screen.Playlists
    )

)