package com.example.belajaronlineapplication.signinup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.belajaronlineapplication.main.MainActivity
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private var blurred: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null)
            updateUI()


        btSignIn.setOnClickListener {
            performSignIn()
        }

        tvSignUp.setOnClickListener {
            val signUpIntent = Intent(
                this@SignInActivity,
                SignUpActivity::class.java
            )
            startActivity(signUpIntent)
        }

        tvForgotPassword.setOnClickListener {
            val forgotIntent = Intent(
                this@SignInActivity,
                ForgotPasswordActivity::class.java
            )
            startActivity(forgotIntent)
        }
    }

    private fun updateUI() {
        val mainIntent = Intent(
            this@SignInActivity,
            MainActivity::class.java
        )
        mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainIntent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

//    intent.putExtra("nama", user.nama)
//    intent.putExtra("alamat", user.alamat)
//    intent.putExtra("email", user.email)
//    intent.putExtra("jenisKelamin", user.jenisKelamin)
//    intent.putExtra("jurusan", user.jurusan)
//    intent.putExtra("kabupatenKota", user.kabupatenKota)
//    intent.putExtra("kurikulum", user.kurikulum)
//    intent.putExtra("namaOrtu", user.namaOrtu)
//    intent.putExtra("noHP", user.noHP)
//    intent.putExtra("noHPOrtu", user.noHPOrtu)
//    intent.putExtra("profileImageUrl", user.profileImageUrl)
//    intent.putExtra("provinsi", user.provinsi)
//    intent.putExtra("sekolah", user.sekolah)

    private fun blurall() {

        if (blurred) {
            Blurry.delete(findViewById<View>(R.id.content) as ViewGroup)
        } else {
            val startMs = System.currentTimeMillis()
            Blurry.with(this@SignInActivity)
                .radius(25)
                .sampling(2)
                .async()
                .animate(500)
                .onto(findViewById<View>(R.id.content) as ViewGroup)
            Log.d(
                "Sign In Activity",
                "TIME " + (System.currentTimeMillis() - startMs).toString() + "ms"
            )
        }
        blurred = !blurred
    }

    private fun performSignIn() {
        password = etPassword.text.toString()
        email = etUsername.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
        blurall()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }

                val user = FirebaseAuth.getInstance().currentUser
                val emailVerified = user?.isEmailVerified

                if (emailVerified == true) {
                    blurall()
                    Log.d("Main", "Successfully login user with uid: ${it.result?.user?.uid}")
                    val mainIntent = Intent(
                        this@SignInActivity,
                        MainActivity::class.java
                    )
                    mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(mainIntent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                } else {
                    blurall()
                    val notVerifiedIntent = Intent(
                        this@SignInActivity,
                        NotVerifiedActivity::class.java
                    )
                    startActivity(notVerifiedIntent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

            }.addOnFailureListener {
                blurall()
                Log.d("SignInActivity", "Failed to login user: ${it.message}")
                Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
            }

        Log.d("Login", "Attempt login with email/pw: $email/***")
    }
}