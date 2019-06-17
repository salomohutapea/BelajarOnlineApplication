package com.example.belajaronlineapplication.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.belajaronlineapplication.R
import kotlinx.android.synthetic.main.activity_hubungi_kami.*
import java.time.temporal.TemporalQueries

class hubungi_kami : AppCompatActivity() {

    val REQUEST_PHONE_CALL = 1
    val phone = "+6282322623225"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hubungi_kami)

        //Akan kembali ke activity sebelumnya jika tombol back diklik
        backbthubungi.setOnClickListener {
            finish()
        }
        //Jika tombol email diklik, akan terbuka aplikasi email yang digunakan user
        //dengan alamat tujuan email yang sudah terisi
        btemailhubungi.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_SEND)
            val recipient = arrayOf("me@salomohutapea.com", "me@rivaldynaiborhu.com")
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, recipient)

            try {
                startActivity(Intent.createChooser(mIntent, "Choose email client"))
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
        //Ketika tombol call ditekan, maka user akan menerima request permission untuk mengizinkan panggilan
        //Setelah disetujui, akan langsung menelepon nomor programmer yang sudah diinisialisasi terlebih dahulu
        btcallhubungi.setOnClickListener{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),REQUEST_PHONE_CALL)
            } else {
                startCall()
            }

        }
    }
    //Fungsi menghubungi nomor programmer
    private fun startCall() {
        val cIntent = Intent (Intent.ACTION_CALL)
        cIntent.data = Uri.parse("tel:" + phone)

        try {
            startActivity(cIntent)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_PHONE_CALL)startCall()
    }
}
