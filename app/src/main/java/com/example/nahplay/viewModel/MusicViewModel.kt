package com.example.nahplay.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nahplay.data.MusicRepository
import com.example.nahplay.data.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class PlaybackState{
    IDLE, PLAYING, PAUSE
}
class MusicViewModel(application: Application) : AndroidViewModel(application)   {
    //instance of music repo
    private val  musicRepository = MusicRepository(application)

    // SONGS
    private  val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs : StateFlow<List<Song>> = _songs.asStateFlow()


    //CURRENT SONG

    private val _currentSong  = MutableStateFlow<Song?>(null)
    val currentSong :StateFlow<Song?> = _currentSong.asStateFlow()

     // SONG STATE
    private val _playbackState = MutableStateFlow(PlaybackState.IDLE)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    // CURRENT SONG PROGRESS
    private val _progressMs = MutableStateFlow(0L)
    val progressMs: StateFlow<Long> = _progressMs.asStateFlow()

    // SONG DURATION
    private val _durationMs = MutableStateFlow(0L)
    val durationMs: StateFlow<Long> = _durationMs.asStateFlow()

    // SONG LIST
    private var queue: List<Song> = emptyList()
    private var currentIndex: Int = -1

    // LIST FOR FAVOURITES
    private val _favorites = MutableStateFlow<List<Song>>(emptyList())
    val favorites: StateFlow<List<Song>> = _favorites.asStateFlow()

    // SEARCH SONG QUERY
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // SEARCHED SONG QUERY
    private val _filteredSongs = MutableStateFlow<List<Song>>(emptyList())
    val filteredSongs: StateFlow<List<Song>> = _filteredSongs.asStateFlow()


    //Initializing the class along with that load the songs by calling load function to load songs
    init{
        loadSongs()
    }

    //Load Songs
    fun loadSongs(){
        viewModelScope.launch(Dispatchers.IO){
            val result = musicRepository.getAllSongs()
            _songs.value = result
            _filteredSongs.value = result
        }
    }

    //Search Song also start search while typing or as value changed
    fun  onSearchQueryChange(query :String){
        _searchQuery.value = query
        _filteredSongs.value = if(query.isBlank()){
            _songs.value
        }else{
            _songs.value.filter{song->
                song.title.contains(query, ignoreCase = true) ||
                        song.artist.contains(query, ignoreCase = true) ||
                        song.album.contains(query, ignoreCase = true)
            }
        }
    }

    //SONG CONTROLS
    fun playSong(song:Song){
        queue  = _songs.value
        currentIndex = queue.indexOfFirst { it.id ==song.id }
        _currentSong.value = song
        _playbackState.value = PlaybackState.PLAYING
        _durationMs.value = song.duration
        _progressMs.value = 0L
    }

    fun playNext(){
        if(queue.isEmpty()) return
        currentIndex = (currentIndex+1)%queue.size
        playSong(queue[currentIndex])
    }

    fun playPrevious(){
        if (queue.isEmpty()) return
        currentIndex = (currentIndex - 1 + queue.size) % queue.size
        playSong(queue[currentIndex])
    }

    fun togglePlayPause(){
        _playbackState.value = when(_playbackState.value){
            PlaybackState.PLAYING -> PlaybackState.PAUSE
            PlaybackState.PAUSE  -> PlaybackState.PLAYING
            PlaybackState.IDLE    -> PlaybackState.IDLE
        }
    }

    fun seekTo(ms: Long) {
        _progressMs.value = ms
    }

    fun updateProgress(ms: Long) {
        _progressMs.value = ms
    }

    //function to add favourite list of song
    fun toggleFavorite(song: Song) {
        val current = _favorites.value.toMutableList()
        if (current.any { it.id == song.id }) {
            current.removeAll { it.id == song.id }
        } else {
            current.add(song)
        }
        _favorites.value = current
    }

    //is song in favourite
    fun isFavourite(song:Song): Boolean{
        return  _favorites.value.any{it.id==song.id}
    }

    //function to help in formatting duration of song
    fun formatDuration(ms:Long):String{
        return musicRepository.formatDuration(ms)
    }
}