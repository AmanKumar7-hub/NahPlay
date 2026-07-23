package com.example.nahplay.player

import android.content.Context
import android.media.MediaPlayer
import com.example.nahplay.data.Song
import com.example.nahplay.viewModel.MusicViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerController (
    private val context: Context,
    private val viewModel: MusicViewModel
){
    private var mediaPlayer: MediaPlayer?=null
    private var progressJob : Job?=null
    private val scope = CoroutineScope(Dispatchers.Main)

    //function to play a song
    fun play(song: Song){
        release()
        try{
            mediaPlayer = MediaPlayer().apply{
                setDataSource(context,song.uri)
                prepare()
                start()
                setOnCompletionListener {
                    viewModel.playNext()
                }
                setOnErrorListener { _, _, _ ->
                    viewModel.playNext()
                    true
                }
            }
            viewModel.playSong(song)
            startProgressTracker()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

//Function to toggle playpause
fun togglePlayPause(){
    val mp = mediaPlayer ?:return
    if(mp.isPlaying){
        mp.pause()
        stopProgressTracker()
    }else{
        mp.start()
        startProgressTracker()
    }
    viewModel.togglePlayPause()
}

    //play next song
    fun playNext(){
        viewModel.playNext()
    }

    //play previous  song
    fun playPrevious(){
        val mp = mediaPlayer
        if(mp!=null && mp.currentPosition>3000){
            mp.seekTo(0)
            viewModel.seekTo(0L)

        }
        else{
            viewModel.playPrevious()
        }
    }

    //play by seeking
    fun seekTo(ms:Long){
        mediaPlayer?.seekTo(ms.toInt())
        viewModel.seekTo(ms)
    }

    //function start progress
    fun startProgressTracker(){
        stopProgressTracker()
        progressJob = scope.launch {
            while(true){
                mediaPlayer?.let{mp->
                    if(mp.isPlaying){
                        viewModel.updateProgress(mp.currentPosition.toLong())
                    }
                }
                delay(500)
            }
        }
    }

    //function to stop progress
    fun stopProgressTracker(){
        progressJob?.cancel()
        progressJob=null
    }
    //function to relase player
    fun release(){
        stopProgressTracker()
        mediaPlayer?.release()
        mediaPlayer=null
    }

    //Check if song is playing
    fun isPlaying():Boolean = mediaPlayer?.isPlaying?:false

    fun currentPosition():Long= mediaPlayer?.currentPosition?.toLong()?:0L
}