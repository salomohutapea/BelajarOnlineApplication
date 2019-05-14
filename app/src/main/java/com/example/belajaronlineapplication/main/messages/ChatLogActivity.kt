package com.example.belajaronlineapplication.main.messages

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.ChatMessage
import com.example.belajaronlineapplication.models.NewMessageUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        recyclerview_chat_log.adapter = adapter
        val user = intent.getParcelableExtra<NewMessageUser>(NewMessageActivity.USER_KEY)
        titleChatLog.text = user.nama

//        setupDummyData()
        listenForMessages()
        arrowBack.setOnClickListener {
            finish()
        }

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message.....")
            performSendMessage()
        }
    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatFromItem(chatMessage.text))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text))
                    }
                }
                Log.d(TAG, chatMessage?.text)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }

    private fun performSendMessage() {
        val text = edittext_chat_log.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<NewMessageUser>(NewMessageActivity.USER_KEY)
        val toId = user.uid
        val ref = FirebaseDatabase.getInstance().getReference("/messages").push()
        if (fromId == null) return

        val chatMessage = ChatMessage(ref.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Save our chat message: ${ref.key}")
            }
    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatFromItem("From message"))
        adapter.add(ChatToItem("To message"))
        adapter.add(ChatFromItem("From message"))
        adapter.add(ChatToItem("To message"))
        adapter.add(ChatFromItem("From message"))
        adapter.add(ChatToItem("To message"))
        adapter.add(ChatFromItem("From message"))
        adapter.add(ChatToItem("To message"))
        recyclerview_chat_log.adapter = adapter
    }
}

class ChatFromItem(val text: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewFrom.text = text
    }
}

class ChatToItem(val text: String) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewFrom.text = text
    }
}
