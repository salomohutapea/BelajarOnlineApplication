package com.example.belajaronlineapplication.main.messages.views

import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.ChatMessage
import com.example.belajaronlineapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage) : Item<ViewHolder>() {
    var chatPartnerUser: User? = null
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textViewMessageLatestMessage.text = chatMessage.text
        //Untuk mengecek uid user yang terakhir mengirimkan pesan
        val chatPartnerId: String = if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatMessage.toId
        } else {
            chatMessage.fromId
        }
        //Mengambil pesan dari firebase dan menampilkan pesan terakhir pada chat tersebut
        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.textViewNameLatestMessage.text = chatPartnerUser?.nama
                val targetImageView = viewHolder.itemView.imageViewLatestMessage
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
            }

        })
    }
}
