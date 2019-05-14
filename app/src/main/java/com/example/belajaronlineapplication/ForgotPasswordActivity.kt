package com.example.belajaronlineapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btReset.setOnClickListener {

            val email = etEmailForgot.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Masukkan email anda", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Link reset password sudah dikirimkan ke email anda!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Gagal mengirimkan email reset password!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
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