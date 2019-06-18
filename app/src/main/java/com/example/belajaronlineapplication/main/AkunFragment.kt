package com.example.belajaronlineapplication.main


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_akun.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AkunFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_akun, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUsers()

        //meng-link semua fitur di akun fragment activity ke ComingSoonActivity
        ubahSandi_textview.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .addOnCompleteListener { task ->

                    //Jika email reset password telah terkirim...
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "Link ubah password sudah dikirimkan ke email anda!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //Jika gagal mengirimkan email reset password, atau user tidak terdapat di firebase authentication
                    else {
                        Toast.makeText(
                            activity,
                            "Gagal mengirimkan email ubah password!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        akunProfilePicture.setOnClickListener {
            val ubahGambarIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(ubahGambarIntent)
        }
    }

    //Untuk mengambil data user dari Firebase Database
    private fun fetchUsers() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/users")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    Log.d("Akun Fragment", it.toString())
                    val user = it.getValue(User::class.java)

                    if (user != null && uid == user.uid) {
                        Picasso.get().load(user.profileImageUrl).into(akunProfilePicture)
                        akunAlamat_textview.text = user.alamat
                        akunEmail_textview.text = user.email
                        akunJenisKelamin_textview.text = user.jenisKelamin
                        akunKabupaten_textview.text = user.kabupatenKota
                        akunKelas_textview.text = user.tingkat
                        akunNamaOrtu_textview.text = user.namaOrtu
                        akunNoTelpOrtu_textview.text = user.noHPOrtu
                        akunNama_textview.text = user.nama
                        akunNoHp_textview.text = user.noHP
                        akunProvinsi_textview.text = user.provinsi
                        akunSekolah_textview.text = user.sekolah
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        }
        )
    }
}