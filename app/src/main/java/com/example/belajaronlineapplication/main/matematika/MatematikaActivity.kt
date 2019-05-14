package com.example.belajaronlineapplication.main.matematika


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.main.matematika.Lesson1.LatihanSoalActivity
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.main.matematika.Lesson1.VideoActivity
import kotlinx.android.synthetic.main.activity_matematika.*

class MatematikaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matematika)

        arrowBack.setOnClickListener{
            super.onBackPressed()
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        llLatihanTopik.setOnClickListener {
            val latihanSoalIntent = Intent(
                this@MatematikaActivity,
                LatihanSoalActivity::class.java
            )
            startActivity(latihanSoalIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        llTopik1.setOnClickListener {
            val videoIntent = Intent(
                this@MatematikaActivity,
                VideoActivity::class.java
            )
            startActivity(videoIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}