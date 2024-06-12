package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import java.util.Timer
import java.util.TimerTask

class PlayMusic : AppCompatActivity() {

    private var media: MediaPlayer? = null
    private var isPlaying = false
    private lateinit var seekBar: SeekBar
    private lateinit var playButton: ImageButton
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_music)

        val text = findViewById<TextView>(R.id.text)
        val img1 = findViewById<ImageView>(R.id.img1)
        playButton = findViewById(R.id.play)
        seekBar = findViewById(R.id.seekBar)

        val intent = intent
        val artist = intent.getStringExtra("artist")
        val song = intent.getStringExtra("song")
        val img = intent.getStringExtra("image")
        playButton.setImageResource(android.R.drawable.ic_media_play)

        text.text = artist
        Picasso.get().load(img).into(img1)

        playButton.setOnClickListener {
            if (!isPlaying) {
                if (media == null) {
                    media = MediaPlayer.create(this, song?.toUri())
                    media?.setOnCompletionListener {
                        resetPlayer()
                    }
                    media?.setOnPreparedListener {
                        seekBar.max = media!!.duration
                        media!!.start()
                        startSeekBarUpdate()
                    }
                } else {
                    media?.start()
                    startSeekBarUpdate()
                }
                isPlaying = true
                playButton.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                media?.pause()
                isPlaying = false
                playButton.setImageResource(android.R.drawable.ic_media_play)
            }
            Toast.makeText(this, if (isPlaying) "Playing" else "Paused", Toast.LENGTH_LONG).show()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    media?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startSeekBarUpdate() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (media != null && isPlaying) {
                    runOnUiThread {
                        seekBar.progress = media!!.currentPosition
                    }
                }
            }
        }, 0, 1000)
    }

    private fun resetPlayer() {
        timer?.cancel()
        timer = null
        media?.release()
        media = null
        isPlaying = false
        playButton.setImageResource(android.R.drawable.ic_media_play)
        seekBar.progress = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        resetPlayer()
    }
}
