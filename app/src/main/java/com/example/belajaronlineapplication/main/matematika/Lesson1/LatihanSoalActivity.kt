package com.example.belajaronlineapplication.main.matematika.Lesson1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_latihan_soal.*

//Activity ini hanya menampilkan latihan soal, dan hanya terdapat 1 button back yang diklik
class LatihanSoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal)
        arrowBackLatihan.setOnClickListener {
            finish()
        }
    }
}