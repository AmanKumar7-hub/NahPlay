package com.example.nahplay.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri

class MusicRepository(private val context: Context) {

    //Get All Songs
    fun getAllSongs():List<Song>{
        val songs = mutableListOf<Song>()

        val collections = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} !=0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        context.contentResolver.query(
            collections,
            projection,
            selection,
            null,
            sortOrder
        )?.use{cursor->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val albumIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val durCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while(cursor.moveToNext()){
                val id = cursor.getLong(idCol)
                val albumId = cursor.getLong(albumIdCol)

                val songUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val albumArtUri = ContentUris.withAppendedId(
                    "content://media/external/audio/albumart".toUri(),
                    albumId
                )

                songs.add(
                    Song(
                        id = id,
                        title = cursor.getString(titleCol) ?: "Unknown",
                        artist      = cursor.getString(artistCol) ?: "Unknown",
                        album       = cursor.getString(albumCol)  ?: "Unknown",
                        duration    = cursor.getLong(durCol),
                        uri         = songUri,
                        albumArtUri = albumArtUri
                    )
                )
            }

        }
        return songs ;
    }

    //Songs with Id
    fun getSongWithId(id:Long):Song?=
        getAllSongs().find{it.id==id}

    fun formatDuration(ms:Long):String{
        val totalSeconds = ms/1000
        val minutes = totalSeconds/60
        val seconds = totalSeconds%60

        return "%d: %02d".format(minutes,seconds)
    }
}