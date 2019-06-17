package com.example.belajaronlineapplication.signinup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //Mengecek apakah sudah ada user yang login
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null)
            updateUI()

        //Ketika button sign in di click maka method perform signin dijalankan
        btSignIn.setOnClickListener {
            performSignIn()
        }
            //ketika tombol sign up di klik, maka akan ditampilkan activity sign up.
        tvSignUp.setOnClickListener {
            val signUpIntent = Intent(
                this@SignInActivity,
                SignUpActivity::class.java
            )
            startActivity(signUpIntent)
        }
        //Ketika di click, maka akan menampilkan activity lupa password.
        tvForgotPassword.setOnClickListener {
            val forgotIntent = Intent(
                this@SignInActivity,
                ForgotPasswordActivity::class.java
            )
            startActivity(forgotIntent)
        }
    }
    //Untuk menutup keyboard
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    //Apabila user sudah login, maka akan user akan diarahkan ke main activity (Home)
    private fun updateUI() {
        val mainIntent = Intent(
            this@SignInActivity,
            MainActivity::class.java
        )
        mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainIntent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
    // Method ini digunakan untuk login menggunakan email & password dengan menggunakan firebase authentication
    private fun performSignIn() {
        password = etPassword.text.toString()
        email = etUsername.text.toString()

        //mengecek apakah textbox email dan password belum diisi, maka akan muncul pesan.
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        //untuk mendisable sentuhan ketika loading screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )

        //menyembunyikan keyboard
        hideKeyboard()
        cardProgressBarSignIn.visibility = View.VISIBLE
        rlProgress.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                }

                val user = FirebaseAuth.getInstance().currentUser
                val emailVerified = user?.isEmailVerified

                // mengecek apakah email user sudah terverifikasi, jika email belum terverifikasi maka akan muncul activity email belum verified
                if (emailVerified == true) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    rlProgress.visibility = View.GONE
                    Log.d("Main", "Successfully login user with uid: ${it.result?.user?.uid}")
                    val mainIntent = Intent(
                        this@SignInActivity,
                        MainActivity::class.java
                    )
                    mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(mainIntent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
                //Jika sudah terverifikasi maka user akan diarahkan ke home activity
                else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    rlProgress.visibility = View.GONE
                    val notVerifiedIntent = Intent(
                        this@SignInActivity,
                        NotVerifiedActivity::class.java
                    )
                    startActivity(notVerifiedIntent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

            }
            //Jika Login gagal maka akan menampilkan pesan
            .addOnFailureListener {
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                cardProgressBarSignIn.visibility = View.GONE
                rlProgress.visibility = View.GONE
                Log.d("SignInActivity", "Failed to login user: ${it.message}")
                Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
            }

        Log.d("Login", "Attempt login with email/pw: $email/***")
    }
}