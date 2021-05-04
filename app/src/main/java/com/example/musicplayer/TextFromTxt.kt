package com.example.musicplayer

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.music_text.*


class TextFromTxt : AppCompatActivity() {

    private var text: TextView? = null
    private var position: Int = 0
    private var next_btn: ImageButton? = null
    private var previous_btn: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_text)


        val file1: String =application.assets.open("lyrics_1.txt").bufferedReader().use { it.readText() }
        val file2: String =application.assets.open("lyrics_2.txt").bufferedReader().use { it.readText() }
        val file3: String =application.assets.open("lyrics_3.txt").bufferedReader().use { it.readText() }
        val file4: String =application.assets.open("lyrics_4.txt").bufferedReader().use { it.readText() }
        val file5: String =application.assets.open("lyrics_5.txt").bufferedReader().use { it.readText() }
        val file6: String =application.assets.open("lyrics_6.txt").bufferedReader().use { it.readText() }
        val file7: String =application.assets.open("lyrics_7.txt").bufferedReader().use { it.readText() }


        val myList1: ArrayList<String>? = arrayListOf(
                file1,
                file2,
                file3,
                file4,
                file5,
                file6,
                file7
        )

        val mIntent = intent
        var intValue = mIntent.getIntExtra("key", 0)

        var currText=myList1!!.get(intValue!!)
        text1.text = currText

        text1.movementMethod = ScrollingMovementMethod.getInstance()

    }


}