package com.example.nahplay.navigation

sealed class Screen(val route:String) {
    object Library    : Screen("library")
    object NowPlaying : Screen("now_playing")
    object Favourites : Screen("favourites")
    object Playlists  : Screen("playlists")
}