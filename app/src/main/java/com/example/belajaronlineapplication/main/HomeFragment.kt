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
import com.example.belajaronlineapplication.main.matematika.MatematikaActivity
import com.example.belajaronlineapplication.main.messages.LatestMessageActivity
import com.example.belajaronlineapplication.models.User
import com.example.belajaronlineapplication.signinup.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    lateinit var textGreetings: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUsers()
        lrMatematika.setOnClickListener {
            val matematikaIntent = Intent(requireActivity(), MatematikaActivity::class.java)
            requireActivity().startActivity(matematikaIntent)
        }
        lrBiologi.setOnClickListener {
            val biologiIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(biologiIntent)
        }
        lrFisika.setOnClickListener {
            val fisikaIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(fisikaIntent)
        }
        lrKimia.setOnClickListener {
            val kimiaIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(kimiaIntent)
        }
        lrIndo.setOnClickListener {
            val indoIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(indoIntent)
        }
        lrInggris.setOnClickListener {
            val inggrisIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(inggrisIntent)
        }
        profile_button.setOnClickListener {
            val profileIntent = Intent(requireActivity(), ComingSoonActivity::class.java)
            requireActivity().startActivity(profileIntent)
        }
        btChat.setOnClickListener {
            val intent = Intent(requireActivity(), LatestMessageActivity::class.java)
            requireActivity().startActivity(intent)
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
                        textGreetings = "Hai " + user.nama + ","
                        Picasso.get().load(user.profileImageUrl).into(profile_button)
                        greetings_textview.text = textGreetings
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        }
        )
    }
}