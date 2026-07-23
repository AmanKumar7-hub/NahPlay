package com.example.nahplay.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.components.MiniPlayer
import com.example.nahplay.viewModel.MusicViewModel

@Composable
fun NavGraph(
    viewModel: MusicViewModel,
    playerController: PlayerController
){
    val navController = rememberNavController()
    val navBackStackEntry  by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute != Screen.NowPlaying.route

    androidx.compose.material3.Scaffold(
        bottomBar = {
            if (showBottomBar) {
                //MiniPlayer above bottom nav
                val currentSong by viewModel.currentSong.collectAsState()
                if(currentSong!=null){
                    MiniPlayer(
                        viewModel = viewModel,
                        playerController = playerController,
                        onExpandClick = {
                            navController.navigate(Screen.NowPlaying.route)
                        }
                    )
                }

                BottomNavBar(
                    items        = bottomNavItems,
                    currentRoute = currentRoute,
                    onItemClick  = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                )
            }
        }
    ) { _ ->

        NavHost(
            navController    = navController,
            startDestination = Screen.Library.route
        ) {
            composable(Screen.Library.route) {
                PlaceholderScreen("Library")
            }

            composable(Screen.NowPlaying.route) {
                PlaceholderScreen("Now Playing")
            }

            composable(Screen.Favourites.route) {
                PlaceholderScreen("Favorites")
            }

            composable(Screen.Playlists.route) {
                PlaceholderScreen("Playlists")
            }
        }
    }
}

@Composable
fun PlaceholderScreen(name:String){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(
            text=name,
            color=Color.White
        )
    }
}