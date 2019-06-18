package com.example.belajaronlineapplication.main.matematika.Lesson1Mat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_latihan_soal_mat1.*

//Activity ini hanya menampilkan latihan soal, dan hanya terdapat 1 button back yang diklik
class LatihanSoalMat1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latihan_soal_mat1)
        //mengarahkan tombol back ke activity sebelumnya
        arrowBackLatihan.setOnClickListener {
            finish()
        }
    }
}