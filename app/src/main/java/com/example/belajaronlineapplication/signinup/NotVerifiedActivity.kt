package com.example.belajaronlineapplication.signinup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_not_verified.*

class NotVerifiedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_verified)

        btNotVerified.setOnClickListener {
            sendEmailVerification()
        }
    }

    //Mengirimkan email verifikasi kepada alamat email yang sudah di inputkan
    private fun sendEmailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Email verifikasi telah dikirim ke " + user.email!!,
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseAuth.getInstance().signOut()

                    val signInIntent = Intent(
                        this@NotVerifiedActivity,
                        SignInActivity::class.java
                    )
                    startActivity(signInIntent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
                else {
                    Log.d("SignInActivity", "sendEmailVerification failed!", task.exception)
                    Toast.makeText(applicationContext, "Gagal mengirimkan email verifikasi.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}