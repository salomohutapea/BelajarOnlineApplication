package com.example.belajaronlineapplication.main.indo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.main.biologi.Lesson1Bio.LatihanSoalBio1
import com.example.belajaronlineapplication.main.biologi.Lesson1Bio.VideoBio1
import com.example.belajaronlineapplication.main.indo.Lesson1Indo.VideoIndo1
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_indo.*

class IndoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indo)
        fetchPicture()
        //Jika button back ditekan, akan kembali ke activity sebelumnya
        arrowBack.setOnClickListener {
            super.onBackPressed()
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //OnClick event untuk button latihan soal
        llLatihanTopik.setOnClickListener {
            val latihanSoalIntent = Intent(
                this@IndoActivity,
                LatihanSoalBio1::class.java
            )
            startActivity(latihanSoalIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //Jika linear layout topik1 diclick, maka akan menampilkan VideIndo1, yang berisi video youtube
        llTopik1.setOnClickListener {
            val videoIntent = Intent(
                this@IndoActivity,
                VideoIndo1::class.java
            )
            startActivity(videoIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    //Method ini untuk mengambil gambar dari firebase storage, dengan bantuan library Picasso
    private fun fetchPicture() {
        //Exercise
        Picasso.get()
            .load("https://firebasestorage.googleapis.com/v0/b/belajaronlineapplication.appspot.com/o/images%2Ff725c5a7-d302-4934-8689-2305ea6e9cd4?alt=media&token=b91db3d6-03b3-4c04-8a1b-6770a66904af")
            .into(bab1Latihan)
        //Topic
        Picasso.get()
            .load("https://firebasestorage.googleapis.com/v0/b/belajaronlineapplication.appspot.com/o/images%2Fbook1.png?alt=media&token=df6c1fda-f42d-4c0e-97e5-85b8b7ecd005")
            .into(bab1_1)

    }
}
