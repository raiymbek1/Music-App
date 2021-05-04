package com.example.musicplayer


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.media.MediaPlayer.create as create


class MainActivity : AppCompatActivity() {

    // initialization and declaration of variable
    private var buttonLyrics: ImageButton? = null


    private var play_btn: ImageButton? = null
    private var next_btn: ImageButton? = null
    private var previous_btn: ImageButton? = null

    private var listOfMusic:List<MediaPlayer>?=null

    private var seekbar: SeekBar? = null
    private var volumebar: SeekBar? = null
    private var currentTime: TextView?=null
    private var finishTime: TextView?=null

    private  var handler = Handler()
    lateinit var runable:Runnable

    private var totalTime: Int=0

    private var icon: ImageView? = null
    var title: TextView? = null
    var title_artist: TextView? = null
    private var position: Int = 0






    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play_btn = findViewById(R.id.play_btn)
        next_btn = findViewById(R.id.next_btn)
        previous_btn = findViewById(R.id.previous_btn)

        currentTime = findViewById(R.id.current_time)
        finishTime = findViewById(R.id.finish_time)

        seekbar = findViewById(R.id.seekBar)
        volumebar = findViewById(R.id.volumeBar)



        icon = findViewById(R.id.icon)
        title = findViewById(R.id.music_title)
        title_artist = findViewById(R.id.music_artist)

        val mediaplayer1: MediaPlayer = create(this, R.raw.song_1)
        val mediaplayer2: MediaPlayer = create(this, R.raw.song_2)
        val mediaplayer3: MediaPlayer = create(this, R.raw.song_3)
        val mediaplayer4: MediaPlayer = create(this, R.raw.song_4)
        val mediaplayer5: MediaPlayer = create(this, R.raw.song_5)
        val mediaplayer6: MediaPlayer = create(this, R.raw.song_6)
        val mediaplayer7: MediaPlayer = create(this, R.raw.song_7)


        val mySong1 = SongClass("Natural","Imagine Dragons", R.drawable.album_1, null, mediaplayer1)
        val mySong2 = SongClass("Apologize","Onerepublic", R.drawable.album_2, null, mediaplayer2)
        val mySong3 = SongClass("Not afraid","Eminem", R.drawable.album_3, null, mediaplayer3)
        val mySong4 = SongClass("Dooset Daram","Arash feat. Helena", R.drawable.album_4, null, mediaplayer4)
        val mySong5 = SongClass("Lucid Dreams","Juice WRLD", R.drawable.album_5, null, mediaplayer5)
        val mySong6 = SongClass("Lonely","Akon", R.drawable.album_6, null, mediaplayer6)
        val mySong7 = SongClass("Praise the Lord","ASAP Rocky", R.drawable.album_7, null, mediaplayer7)

        val myList1: ArrayList<SongClass>? = arrayListOf(
                mySong1,
                mySong2,
                mySong3,
                mySong4,
                mySong5,
                mySong6,
                mySong7

        )

        var currMusic: SongClass = myList1!!.get(position)
        var currSong: MediaPlayer = currMusic.song!!
        totalTime = currSong.duration
        title!!.setText(currMusic.name!!)
        title_artist!!.setText(currMusic.title!!)
        icon!!.setImageResource(currMusic.photo!!)
        var currbitmapIcon: Bitmap? =BitmapFactory.decodeResource(applicationContext.resources, currMusic.photo!!)







        seekbar!!.progress =0
        seekbar!!.max=totalTime
        buttonLyrics = findViewById(R.id.button_text)

        buttonLyrics?.setOnClickListener(View.OnClickListener {
            openLyrics(position)

        })






        //process of pause and resume
        play_btn?.setOnClickListener{

            if (!currSong.isPlaying) {
                currSong.start()
                play_btn!!.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                currSong.pause()
                play_btn!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }

        }
        //////////////////////////////////


        next_btn?.setOnClickListener {

            currSong.pause()
            play_btn!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)

            if (position < 6) {
                position = position + 1
            } else {
                position = 0
            }


            currMusic = myList1!!.get(position)
            currSong = currMusic.song!!
            totalTime = currSong.duration
            title!!.setText(currMusic.name!!)
            title_artist!!.setText(currMusic.title!!)
            icon!!.setImageResource(currMusic.photo!!)
            currbitmapIcon=BitmapFactory.decodeResource(applicationContext.resources, currMusic.photo!!)

            currSong.start()

            play_btn!!.setImageResource(R.drawable.ic_baseline_pause_24)


        }

        //process of previous song
        previous_btn?.setOnClickListener {

            currSong.pause()
            play_btn!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)

            if (position > 0) {
                position = position - 1
            } else {
                position = 6
            }

            currMusic = myList1!!.get(position)
            currSong = currMusic.song!!
            totalTime = currSong.duration
            title!!.setText(currMusic.name!!)
            title_artist!!.setText(currMusic.title!!)
            icon!!.setImageResource(currMusic.photo!!)
            currbitmapIcon=BitmapFactory.decodeResource(applicationContext.resources, currMusic.photo!!)

            currSong.start()

            play_btn!!.setImageResource(R.drawable.ic_baseline_pause_24)
        }
        //////////////////////////////////




        // Volume Bar
        volumebar!!.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        var volumeNum = progress / 100.0f
                        currSong.setVolume(volumeNum, volumeNum)
                    }
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )


        //process of progress bar
        seekbar!!.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
                if (changed){
                    currSong.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        runable = Runnable {
            seekbar!!.progress = currSong.currentPosition
            handler?.postDelayed(runable!!,1000)

        }
        handler?.postDelayed(runable!!,1000)


        currSong.setOnCompletionListener{
            play_btn!!.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            seekbar!!.progress=0
        }
        /////////////////////////////////////////////////////////////////////////


        //process of increasing and decreasing time
        Thread(Runnable {
            while (currSong != null) {
                try {
                    var msg = Message()
                    msg.what = currSong.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()


        @SuppressLint("HandlerLeak")
        handler= object: Handler(){

            override fun handleMessage(msg: Message) {
                var currentPosition =msg.what


                var newInt = totalTime - currentPosition
                var newFunc = newInt/1000%60
                var min = newInt/1000/60


                var elapsedTime =createTimeLabel(currentPosition)
                //set text
                currentTime!!.setText(elapsedTime)

                var remainingTime=createTimeLabel(totalTime-currentPosition)
                //set text
                finishTime!!.setText("-$remainingTime")

                createNotificationChannel()

                if(newFunc == 10 && min == 0){

                    sendNotification()


                }
            }
        }

    }


    private val CHANNEL_ID = "channel_id_01"
    private val notificationId = 101

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Noti Name"
            val descriptionText = "some desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description= descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.headphones)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.song)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
            .setContentTitle("Next track")
            .setContentText("It will start after 10 seconds!")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }


    fun createTimeLabel(time: Int): String {
        var timelabel = ""
        var min = time/1000/60
        var sec = time/1000%60
        timelabel="$min:"


        if (sec<10){

            timelabel +="0"

        }

        timelabel +=sec

        return timelabel
    }


    fun openLyrics(i: Int) {

        val intent = Intent(this, TextFromTxt::class.java)
        intent.putExtra("key", i);
        startActivity(intent)
    }

    /////////////////////////////////////////////////////////////////////////////////
}