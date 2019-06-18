package com.example.belajaronlineapplication.main.kimia.Lesson1Kimia

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_latihan_soal_kim1.*

class LatihanSoalKim1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal_kim1)
        //mengarahkan tombol panah ke activity sebelumnya
        arrowBackLatihan.setOnClickListener {
            finish()
        }
    }
}