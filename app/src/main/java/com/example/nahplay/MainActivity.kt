package com.example.nahplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nahplay.navigation.NavGraph
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.theme.NahPlayTheme
import com.example.nahplay.viewModel.MusicViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MusicViewModel by viewModels()
    private lateinit var playerController: PlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerController = PlayerController(this,viewModel)

        viewModel.currentSong.value?.let{song->
            playerController.play(song)
        }

//        enableEdgeToEdge()
        setContent {
            NahPlayTheme {
                NavGraph(
                    viewModel,
                    playerController
                )
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        playerController.release()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NahPlayTheme(
        {
            Greeting("Android")
        },
    )
}