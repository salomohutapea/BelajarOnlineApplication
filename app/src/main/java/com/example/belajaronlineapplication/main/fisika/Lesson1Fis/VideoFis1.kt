package com.example.belajaronlineapplication.main.fisika.Lesson1Fis

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video_fis1.*

class VideoFis1 : YouTubeBaseActivity() {

    //Data untuk API youtube
    companion object {
        val VIDEO_ID: String = "wYKEiLznHlE" //ID video youtube
        val YOUTUBE_API_KEY: String = "AIzaSyCZl_NLt-sLAqSvzmXn6BnL-iLmbd1fVfk" //API KEY Youtube
    }

    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_fis1)

        initUI()

        youtubePlayer2.initialize(YOUTUBE_API_KEY, youtubePlayerInit)
        // Tombol back untuk kembali ke activity sebelumnya
        arrowBackVideo.setOnClickListener {
            finish()
        }
    }

    //Method ini untuk menampilkan video youtube
    private fun initUI() {
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Toast.makeText(applicationContext, "Something went wrong !!", Toast.LENGTH_SHORT).show()
            }

            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlayer2: YouTubePlayer?,
                p2: Boolean
            ) {
                youtubePlayer2?.loadVideo(VIDEO_ID)
            }
        }
    }
}