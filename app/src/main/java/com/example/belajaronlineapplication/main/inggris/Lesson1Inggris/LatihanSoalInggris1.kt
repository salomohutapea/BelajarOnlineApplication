package com.example.belajaronlineapplication.main.inggris.Lesson1Inggris

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_latihan_soal_inggris1.*

class LatihanSoalInggris1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal_inggris1)
        arrowBackLatihan.setOnClickListener {
            finish()
    }
}}
