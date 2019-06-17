package com.example.belajaronlineapplication.main.fisika.Lesson1Fis

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_latihan_soal_fis1.*

class LatihanSoalFis1 : AppCompatActivity() {

    //Menghubungan arrow back kembali ke menu sebelumnya
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal_fis1)
        arrowBackLatihan.setOnClickListener {
            finish()

        }
    }
}
