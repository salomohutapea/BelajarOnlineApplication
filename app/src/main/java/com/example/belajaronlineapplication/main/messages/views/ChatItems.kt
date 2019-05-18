package com.example.belajaronlineapplication.main.messages.views

import com.example.belajaronlineapplication.R
import com.example.belajaronlineapplication.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*


class ChatFromItem(val text: String, val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageViewFrom
        Picasso.get().load(uri).into(targetImageView)
        viewHolder.itemView.textViewFrom.text = text
    }
}

class ChatToItem(val text: String, val user: User) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageViewTo
        Picasso.get().load(uri).into(targetImageView)
        viewHolder.itemView.textViewTo.text = text
    }
}