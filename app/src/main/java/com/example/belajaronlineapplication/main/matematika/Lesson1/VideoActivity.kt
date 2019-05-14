package com.example.belajaronlineapplication.main.matematika.Lesson1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.signinup.SignInActivity
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : YouTubeBaseActivity() {

    companion object {
        val VIDEO_ID: String = "P3FEIS4Yvr0"
        val YOUTUBE_API_KEY: String = "AIzaSyCZl_NLt-sLAqSvzmXn6BnL-iLmbd1fVfk"
    }

    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        VerifyUserIsLoggedIn()

        initUI()

        youtubePlayer1.initialize(YOUTUBE_API_KEY, youtubePlayerInit)

        arrowBackVideo.setOnClickListener {
            finish()
        }
    }

    private fun VerifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

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