package com.example.belajaronlineapplication.main.matematika.Lesson1

import android.os.Bundle
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : YouTubeBaseActivity() {
    //Data untuk API youtube
    companion object {
        val VIDEO_ID: String = "P3FEIS4Yvr0" //ID video youtube berasal dari : contoh www.youtu.be/P3FEIS4Yvr0
        val YOUTUBE_API_KEY: String = "AIzaSyCZl_NLt-sLAqSvzmXn6BnL-iLmbd1fVfk" //API KEY Youtube
    }

    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        initUI()

        youtubePlayer1.initialize(YOUTUBE_API_KEY, youtubePlayerInit)
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
                youtubePlayer1: YouTubePlayer?,
                p2: Boolean
            ) {
                youtubePlayer1?.loadVideo(VIDEO_ID)
            }
        }
    }
}