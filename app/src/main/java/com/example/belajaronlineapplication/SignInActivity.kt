package com.example.belajaronlineapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private var blurred: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        lrProgressBar.visibility = View.GONE

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

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
    }

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
            lrProgressBar.visibility = View.GONE
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
        blurall()
        lrProgressBar.visibility = View.VISIBLE
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
                    lrProgressBar.visibility = View.GONE
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
                    lrProgressBar.visibility = View.GONE
                    val notVerifiedIntent = Intent(
                        this@SignInActivity,
                        NotVerifiedActivity::class.java
                    )
                    startActivity(notVerifiedIntent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

            }.addOnFailureListener {
                blurall()
                lrProgressBar.visibility = View.GONE
                Log.d("SignInActivity", "Failed to login user: ${it.message}")
                Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
            }

        Log.d("Login", "Attempt login with email/pw: $email/***")

        lrProgressBar.visibility = View.GONE
    }
}