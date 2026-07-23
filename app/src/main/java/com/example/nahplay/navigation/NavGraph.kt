package com.example.nahplay.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.components.MiniPlayer
import com.example.nahplay.ui.screens.FavoritesScreen
import com.example.nahplay.ui.screens.LibraryScreen
import com.example.nahplay.ui.screens.NowPlayingScreen
import com.example.nahplay.ui.screens.PlaylistsScreen
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

    Scaffold(
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
    ) { innerPadding ->

        NavHost(
            navController    = navController,
            startDestination = Screen.Library.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Library.route) {
                LibraryScreen(
                    viewModel = viewModel,
                    playerController = playerController,
                    paddingValues =PaddingValues (0.dp )
                )
            }

            composable(Screen.NowPlaying.route) {
                NowPlayingScreen(
                    viewModel = viewModel,
                    playerController = playerController,
                    onBack = { navController.popBackStack() },
                    paddingValues = PaddingValues(0.dp),
                )
            }

            composable(Screen.Favourites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    playerController = playerController,
                    //onBack = { navController.popBackStack()},
                    paddingValues = PaddingValues(0.dp)
                )
            }

            composable(Screen.Playlists.route) {
                PlaylistsScreen(
                    viewModel = viewModel,
                    paddingValues = PaddingValues(0.dp)
                )
            }
        }
    }
}

//@Composable
//fun PlaceholderScreen(name:String){
//    Box(modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center){
//        Text(
//            text=name,
//            color=Color.White
//        )
//    }
//}