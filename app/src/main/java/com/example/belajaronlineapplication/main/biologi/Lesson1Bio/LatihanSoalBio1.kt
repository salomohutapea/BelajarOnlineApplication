package com.example.belajaronlineapplication.main.biologi.Lesson1Bio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_latihan_soal_bio1.*

class LatihanSoalBio1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal_bio1)
        arrowBackLatihan.setOnClickListener {
            finish()
        }
    }
}
