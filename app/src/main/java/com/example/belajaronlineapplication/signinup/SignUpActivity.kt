package com.example.belajaronlineapplication.signinup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
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

        //Jika button select profile di click maka, akan muncul pilihan foto dari galery
        selectPhotoButton.setOnClickListener {
            Log.d("RegisterActivity", "Try to show photo selector")

            val intentPhotoSelector = Intent(Intent.ACTION_PICK)
            intentPhotoSelector.type = "image/*"
            startActivityForResult(intentPhotoSelector, 0)
        }

        //Jika tvSignIn di click, maka SignInActivity akan terbuka
        tvSignIn.setOnClickListener {
            val signInIntent = Intent(
                this@SignUpActivity,
                SignInActivity::class.java
            )
            startActivity(signInIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        //Jika button signup di click maka method perform register akan dijalankan
        btSignUp.setOnClickListener {
            performRegister()
        }

        //untuk memberi 3 pilihan kategori tingkat di spinner
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
                // Menampilkan selected item di text view
                tvSpinnerGrade.text = "${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        //Untuk memberi 2 pilihan kategori jurusan di spinner
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
                // Menampilkan selected item di spinner major
                tvSpinnerMajor.text = "${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        // Untuk menampilkan 3 pilihan kurikulum di spinner
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

        //Untuk menampilkan 2 pilihan gender
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

    //Method untuk mengupload foto
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

    //Untuk menyembunyikan keyboard
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

    //fungsi untuk register
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

        //Jika belum ada foto yang dipilih, maka akan muncul pesan
        if (selectedPhotoUri == null) {
            Toast.makeText(
                applicationContext,
                "Please upload your photo",
                Toast.LENGTH_SHORT
            ).show()
        }

        //Untuk Mengecek apakah email, password dan nama tidak kosong.
        if (email.isEmpty() || password.isEmpty() || nama.isEmpty()) {
            Toast.makeText(this, "Masukkan email, password, dan nama", Toast.LENGTH_SHORT).show()
            return
        }

        //Untuk mengecek apakah agreeCheckBox sudah di check
        if (!agreeCheckBox.isChecked) {
            Toast.makeText(this, "Please agree to our terms and conditions", Toast.LENGTH_SHORT).show()
            return
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
//        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        hideKeyboard()
        cardProgressBarSignUp.visibility = View.VISIBLE

        //Untuk membuat akun aplikasi dari user berdasarkan email dan password yang sudah di input
        //menggunakan firebase authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                //else if successful
                Log.d("SignUpActivity", "Successfully created user with uid: ${it.result?.user?.uid}")
                sendEmailVerification()
            }.addOnFailureListener {
                Log.d("SignUpActivity", "Failed to create user: ${it.message}")
                cardProgressBarSignUp.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    //untuk mengirimkan email verifikasi ke email user yang sudah diinputkan
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
                cardProgressBarSignUp.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(applicationContext, "Gagal mengirimkan email verifikasi.", Toast.LENGTH_SHORT).show()
            }
    }

    //Untuk mengupload menyimpan foto pilihan user ke firebase storage
    private fun uploadImageToFirebaseStorage() {
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
                cardProgressBarSignUp.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Toast.makeText(applicationContext, "Gagal menyimpan data user.", Toast.LENGTH_SHORT).show()
                Log.d("SignUpActivity", "Failed to upload image to firebase database")
            }
    }

    //Setelah email verifikasi telah terkirim dan foto sudah tersimpan ke firebase storage, Maka data user akan tersimpan
    //di FirebaseDataBase
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

        // Jika data user tersimpan di database maka akan terbuka lagi activity signIn
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

            // Jika gagal memasukkan ke database maka, akan keluar pesan.
            .addOnFailureListener {
                cardProgressBarSignUp.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
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