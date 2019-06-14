package com.example.belajaronlineapplication.main.messages


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.belajaronlineapplication.models.User
import com.example.belajaronlineapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        //Onclick event tombol back
        arrowBack.setOnClickListener {
            finish()
        }
        //Menjalankan method FetchUser()
        FetchUser()
        recyclerview_newMessage.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }
    //Mengambil data TUTOR dan dimasukkan ke dalam adapter
    private fun FetchUser() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<ViewHolder>()
                p0.children.forEach {
                    Log.d("New Message", it.toString())
                    val userNewMessage = it.getValue(User::class.java)
                    if (userNewMessage != null) {
                        if (userNewMessage.uid != FirebaseAuth.getInstance().uid) {
                            //Membuat setiap user yang login dengan password ini akan muncul di NewMessage, yang berarti tutor
                            if(userNewMessage.password == "tutormat" || userNewMessage.password == "tutorfis" || userNewMessage.password == "tutoring" || userNewMessage.password == "tutorbio" || userNewMessage.password == "tutorind" || userNewMessage.password == "tutorkim")
                                adapter.add(UserItem(userNewMessage))
                        }
                    }
                }
                //Untuk mengklik setiap user yang terdapat di new message
                adapter.setOnItemClickListener {item, view ->
                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                }
                recyclerview_newMessage.adapter = adapter
            }
        })
    }
}

class UserItem(val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView.text = user.nama
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView)
    }
}
