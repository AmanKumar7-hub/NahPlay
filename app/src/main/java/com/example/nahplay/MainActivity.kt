package com.example.nahplay

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.nahplay.navigation.NavGraph
import com.example.nahplay.player.PlayerController
import com.example.nahplay.ui.theme.Black
import com.example.nahplay.ui.theme.NahPlayTheme
import com.example.nahplay.ui.theme.TextSecondary
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
                PermissionHandler(
                    onPermissionGranted = {
                        viewModel.loadSongs()
                        NavGraph(viewModel,playerController)
                    }
                ) { }

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

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    NahPlayTheme(
//        {
//        },
//    )
//}

//Ask for permission to handle the audios
@Composable
fun PermissionHandler(
    onPermissionGranted: @Composable ()->Unit,
    onPermissionDenied: @Composable ()->Unit,
){
    val context = LocalContext.current

    val permission = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_AUDIO
    }else{
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var permissionGranted by remember{
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permission
            )== PackageManager.PERMISSION_GRANTED

        )
    }

    var permissionDenied by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {granted->
        permissionGranted = granted
        permissionDenied = !granted
    }
    when{
        permissionGranted -> onPermissionGranted()
        permissionDenied -> onPermissionDenied()
        else->{
            LaunchedEffect(Unit){
                launcher.launch(permission)
            }
            Box(
                Modifier.fillMaxSize()
                    .background(Black),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text= "Permission read audio files",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}