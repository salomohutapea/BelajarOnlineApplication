package com.example.belajaronlineapplication.signinup

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        selectPhotoButton.setOnClickListener {
            Log.d("RegisterActivity", "Try to show photo selector")

            val intentPhotoSelector = Intent(Intent.ACTION_PICK)
            intentPhotoSelector.type = "image/*"
            startActivityForResult(intentPhotoSelector, 0)
        }

        tvSignIn.setOnClickListener {
            val signInIntent = Intent(
                this@SignUpActivity,
                SignInActivity::class.java
            )
            startActivity(signInIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        btSignUp.setOnClickListener {
            performRegister()
        }

        val grades = arrayOf("-", "10 SMA", "11 SMA", "12 SMA")
        val adapterGrades = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            grades // Array
        )
        adapterGrades.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerGrade.adapter = adapterGrades
        spinnerGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                tvSpinnerGrade.text = "${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        val major = arrayOf("-", "IPA", "IPS")
        val adapterMajor = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            major // Array
        )
        adapterMajor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerMajor.adapter = adapterMajor
        spinnerMajor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                tvSpinnerMajor.text = "${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        val curriculum = arrayOf("-", "K13 Revisi", "K13", "KTSP")
        val adapterKurikulum = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            curriculum // Array
        )
        adapterKurikulum.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerKurikulum.adapter = adapterKurikulum
        spinnerKurikulum.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                tvSpinnerKurikulum.text = "${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        val gender = arrayOf("-", "Laki - Laki", "Perempuan")
        val adapterJenisKelamin = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            gender // Array
        )
        adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerJenisKelamin.adapter = adapterJenisKelamin
        spinnerJenisKelamin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view
                tvSpinnerJenisKelamin.text = "${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was...
            Log.d("SignUpActivity", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            selectPhotoButton.setBackgroundDrawable(bitmapDrawable)
            pilihFotoTextView.text = ""
        }
    }

    private fun performRegister() {
        val password = etPasswordSignUp.text.toString()
        val email = etEmail.text.toString()
        val nama = etFullName.text.toString()
        val username = etUsername.text.toString()
        val tingkat = tvSpinnerGrade.text.toString()
        val jurusan = tvSpinnerMajor.text.toString()
        val kurikulum = tvSpinnerKurikulum.text.toString()
        val jenisKelamin = tvSpinnerJenisKelamin.text.toString()
        val noHP = etPhoneNumber.text.toString()
        val alamat = etAlamatSignUp.text.toString()
        val provinsi = etProvinsiSignUp.text.toString()
        val kabupatenKota = etKabKotSignUp.text.toString()
        val sekolah = etSekolahSignUp.text.toString()
        val namaOrtu = etNamaOrtuSignUp.text.toString()
        val noHpOrtu = etNoOrtu.text.toString()
        val agreeIsChecked = agreeCheckBox.isChecked.toString()

        Log.d("SignUpActivity", "Email adalah" + email)
        Log.d("SignUpActivity", "Username adalah" + username)
        Log.d("SignUpActivity", "Password adalah $password")
        Log.d("SignUpActivity", "Nama adalah" + nama)
        Log.d("SignUpActivity", "Tingkat adalah" + tingkat)
        Log.d("SignUpActivity", "Jurusan adalah" + jurusan)
        Log.d("SignUpActivity", "Kurikulum adalah" + kurikulum)
        Log.d("SignUpActivity", "Jenis kelamin adalah" + jenisKelamin)
        Log.d("SignUpActivity", "No Hp adalah +62" + noHP)
        Log.d("SignUpActivity", "Alamat adalah $alamat")
        Log.d("SignUpActivity", "Provinsi adalah $provinsi")
        Log.d("SignUpActivity", "Kabupaten/Kota adalah $kabupatenKota")
        Log.d("SignUpActivity", "Sekolah adalah $sekolah")
        Log.d("SignUpActivity", "Nama orangtua adalah $namaOrtu")
        Log.d("SignUpActivity", "No hp orangtua adalah $noHpOrtu")
        Log.d("SignUpActivity", "Agree  $agreeIsChecked")

        if (email.isEmpty() || password.isEmpty() || nama.isEmpty()) {
            Toast.makeText(this, "Masukkan email, password, dan nama", Toast.LENGTH_SHORT).show()
            return
        }

        if (!agreeCheckBox.isChecked) {
            Toast.makeText(this, "Please agree to our terms and conditions", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                //else if successful
                Log.d("SignUpActivity", "Successfully created user with uid: ${it.result?.user?.uid}")
                sendEmailVerification()
            }.addOnFailureListener {
                Log.d("SignUpActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

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
                    uploadImageToFirebaseStorage()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
            .addOnFailureListener(this) { task ->
                Toast.makeText(applicationContext, "Gagal mengirimkan email verifikasi.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) {
            Toast.makeText(
                applicationContext,
                "Please upload your photo",
                Toast.LENGTH_SHORT
            ).show()
        } else if (selectedPhotoUri != null) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d("SignUpActivity", "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d("SignUpActivity", "File location: $it")
                        saveUserToFirebaseDataBase(it.toString())
                    }
                }
                .addOnFailureListener {
                    Log.d("SignUpActivity", "Failed to upload image to firebase database")
                }
        }
    }

    private fun saveUserToFirebaseDataBase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(
            uid,
            etUsername.text.toString(),
            profileImageUrl,
            etPasswordSignUp.text.toString(),
            etEmail.text.toString(),
            etFullName.text.toString(),
            tvSpinnerGrade.text.toString(),
            tvSpinnerMajor.text.toString(),
            tvSpinnerKurikulum.text.toString(),
            tvSpinnerJenisKelamin.text.toString(),
            "+62" + etPhoneNumber.text.toString(),
            etAlamatSignUp.text.toString(),
            etProvinsiSignUp.text.toString(),
            etKabKotSignUp.text.toString(),
            etSekolahSignUp.text.toString(),
            etNamaOrtuSignUp.text.toString(),
            "+62" + etNoOrtu.text.toString(),
            agreeCheckBox.isChecked.toString()
        )

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("SignUpActivity", "Finally we saved the user to our Firebase Database")
                Toast.makeText(
                    applicationContext,
                    "Sign up success",
                    Toast.LENGTH_SHORT
                ).show()
                FirebaseAuth.getInstance().signOut()
                val Intent = Intent(
                    this@SignUpActivity,
                    SignInActivity::class.java
                )
                startActivity(Intent)
            }
            .addOnFailureListener {
                Log.d("SignUpActivity", "Failed to save user to database")
                Toast.makeText(
                    applicationContext,
                    "Sign up failed",
                    Toast.LENGTH_SHORT
                ).show()
                return@addOnFailureListener
            }
    }
}