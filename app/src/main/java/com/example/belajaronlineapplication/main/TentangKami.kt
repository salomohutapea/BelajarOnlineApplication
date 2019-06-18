package com.example.belajaronlineapplication.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_tentang_kami.*

class TentangKami : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_kami)
        //Mengarahkan tombol back ke activity sebelumya
        arrowBack.setOnClickListener {
            finish()
        }

        // Mengarahkan tombol button ke alamat url yang sudah di hubungkan
        emailSal.setOnClickListener {
            val url = "https://www.salomohutapea.com/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        // Mengarahkan tombol button ke alamat url yang sudah di hubungkan
        emailAldy.setOnClickListener {
            val url = "https://www.rivaldynaiborhu.com/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}