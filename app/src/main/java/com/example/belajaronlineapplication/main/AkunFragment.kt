package com.example.belajaronlineapplication.main


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        ubahNoHp_textview.setOnClickListener {
            val ubahNoHpIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(ubahNoHpIntent)
        }
        ubahSandi_textview.setOnClickListener {
            val ubahSandiIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(ubahSandiIntent)
        }
        tvUbahDetailProfil.setOnClickListener {
            val ubahDetailProfilIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(ubahDetailProfilIntent)
        }
        akunProfilePicture.setOnClickListener {
            val ubahGambarIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(ubahGambarIntent)
        }
    }

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