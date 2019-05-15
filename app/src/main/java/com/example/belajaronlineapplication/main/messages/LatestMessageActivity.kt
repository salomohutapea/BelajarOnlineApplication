package com.example.belajaronlineapplication.main.messages


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.NewMessageUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_latest_message.*

class LatestMessageActivity : AppCompatActivity() {

    companion object {
        var currentUser: NewMessageUser? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_message)
        fetchCurrentUser()
        btNewMessage.setOnClickListener {
            val intent = Intent(this, NewMessageActivity::class.java)
            startActivity(intent)
        }

        arrowBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(NewMessageUser::class.java)
                Log.d("LatestMessages", "Current user is  ${currentUser?.profileImageUrl}")
            }

        })
    }
}