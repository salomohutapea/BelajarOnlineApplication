package com.example.belajaronlineapplication.signinup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btReset.setOnClickListener {

            //Jika button reset di click maka akan mengirimkan email reset password ke email yang sudah di input user sebelumnya.
            val email = etEmailForgot.text.toString()

            //untuk mengecek apakah edit text kosong atau tidak
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Masukkan email anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

                //ketika email sudah terisi ...
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        //Jika email reset password telah terkirim...
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Link reset password sudah dikirimkan ke email anda!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        //Jika gagal mengirimkan email reset password, atau user tidak terdapat di firebase authentication
                        else {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Gagal mengirimkan email reset password!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
        // untuk kembali ke signIn activity
        tvSignInForgot.setOnClickListener {
            val signInIntent = Intent(
                this@ForgotPasswordActivity,
                SignInActivity::class.java
            )
            startActivity(signInIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}