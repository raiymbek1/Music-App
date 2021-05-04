package com.example.musicplayer

import android.media.MediaPlayer
import java.io.File

class SongClass(name:String, title:String, photo: Int, text: File?, song: MediaPlayer) {
    var name: String?=null
    var title: String?=null
    var photo: Int?=0
    var text: File?=null
    var song:MediaPlayer?=null

    init {
        this.name=name
        this.title=title
        this.photo=photo
        this.text=text
        this.song=song
    }
}